/*******************************************************************************
 * Copyright (c) 2021 Composent Inc., and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.provider.grpc.GRPCConstants;
import org.eclipse.ecf.remoteservice.client.AbstractClientContainer;
import org.eclipse.ecf.remoteservice.client.AbstractRSAClientService;
import org.eclipse.ecf.remoteservice.client.RemoteServiceClientRegistration;
import org.eclipse.equinox.concurrent.future.IExecutor;
import org.eclipse.equinox.concurrent.future.IProgressRunnable;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.AbstractStub;

@SuppressWarnings("unused")
public class GRPCClientService extends AbstractRSAClientService {

	private URI uri;
	private ManagedChannel channel;
	@SuppressWarnings("rawtypes")
	private io.grpc.stub.AbstractStub stub;
	private final List<Method> stubMethods = new ArrayList<Method>();

	public GRPCClientService(AbstractClientContainer container, RemoteServiceClientRegistration registration, URI uri) {
		super(container, registration);
		this.uri = uri;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (channel != null && !channel.isShutdown()) {
			channel.shutdown();
			channel = null;
		}
	}
	
	protected Class<?> loadGrpcStubClass(RemoteServiceClientRegistration registration) throws ClassNotFoundException {
		// Get grcpStubClassname from properties
		String grcpClassname = (String) registration.getProperty(GRPCConstants.GRPC_STUB_CLASS_PROP);
		// Load grcpStubClass
		ClassLoader cl = this.registration.getClassLoader();
		if (cl == null) cl = this.getClass().getClassLoader();
		return Class.forName(grcpClassname, true, cl);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Object createProxy(ClassLoader cl, Class[] classes) {
		((GRPCClientContainer.GRPCClientRegistration) this.registration).setClassLoader(cl);
		// Get URI from connected ID
		Method method = null;
		try {
			method = loadGrpcStubClass(registration).getMethod(GRPCConstants.GRPC_STUB_METHOD_NAME, io.grpc.Channel.class);
		} catch (Exception e) {
			throw new RuntimeException("Could not load Grpc Stub class or get stub method from class",e);
		}
		this.channel = ManagedChannelBuilder.forAddress(uri.getHost(), uri.getPort()).usePlaintext().build();
		// Invoke method with channel to get stub
		try {
			this.stub = (io.grpc.stub.AbstractStub) method.invoke(null, channel);
		} catch (Exception e) {
			closeChannel(); 
			throw new RuntimeException("Could not create grpc stub for channel", e);
		}
		// setup stubMethods with the public methods on this.stub class
		Arrays.asList(this.stub.getClass().getDeclaredMethods()).forEach(m -> stubMethods.add(m));
		return super.createProxy(cl, classes);
	}

	private void closeChannel() {
		if (this.channel != null && !this.channel.isShutdown()) {
			this.channel.shutdown();
			this.channel = null;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Object invokeAsync(RSARemoteCall remoteCall) throws ECFException {
		final CompletableFuture cf = new CompletableFuture();
		IExecutor executor = getIFutureExecutor(remoteCall);
		if (executor == null)
			throw new ECFException("no executor available to invoke asynchronously");
		executor.execute(new IProgressRunnable() {
			@Override
			public Object run(IProgressMonitor arg0) throws Exception {
				try {
					Object[] params = remoteCall.getParameters();
					// Get type of params as class array
					Class[] paramTypes = (params == null) ? new Class[0]
							: Arrays.asList(params).stream().map(p -> p.getClass()).collect(Collectors.toList())
									.toArray(new Class[params.length]);
					// Get invokeMethod by comparing against blockingStubMethods
					Method invokeMethod = stubMethods.stream().filter(m -> {
						return remoteCall.getMethod().equals(m.getName())
								&& compareParameterTypes(paramTypes, m.getParameterTypes());
					}).findFirst().get();
					if (invokeMethod == null)
						throw new ECFException("Cannot find matching invokeMethod on grpc blockingStub");
					// invoke and set result as cf result
					cf.complete(invokeMethod.invoke(stub, remoteCall.getParameters()));
				} catch (Exception e) {
					cf.completeExceptionally(e);
				}
				return null;
			}
		}, null);
		return cf;
	}

	@SuppressWarnings("rawtypes")
	private boolean compareParameterTypes(Class[] first, Class[] second) {
		if (first.length != second.length)
			return false;
		for (int i = 0; i < first.length; i++)
			if (!first[i].equals(second[i]))
				return false;
		return true;
	}

	private Method findInvokeMethod(RSARemoteCall remoteCall) throws ECFException {
		Method rcMethod = remoteCall.getReflectMethod();
		Optional<Method> optInvokeMethod = stubMethods.stream().filter(m -> {
			return rcMethod.getName().equals(m.getName())
					&& compareParameterTypes(rcMethod.getParameterTypes(), m.getParameterTypes());
		}).findFirst();
		if (!optInvokeMethod.isPresent()) {
			throw new ECFException("Cannot find matching invokeMethod on grpc stub=" + stub);
		}
		return optInvokeMethod.get();
	}

	@Override
	protected Object invokeSync(RSARemoteCall remoteCall) throws ECFException {
		try {
			return findInvokeMethod(remoteCall).invoke(this.stub, remoteCall.getParameters());
		} catch (Exception e) {
			ECFException ee = new ECFException("Cannot invoke method on grcp stub=" + stub, e);
			ee.setStackTrace(e.getStackTrace());
			throw ee;
		}
	}

}
