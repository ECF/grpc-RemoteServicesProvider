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

import java.lang.reflect.Method;
import java.net.URI;
import java.util.UUID;

import org.eclipse.ecf.core.identity.URIID;
import org.eclipse.ecf.provider.grpc.GRPCConstants;
import org.eclipse.ecf.provider.grpc.identity.GRPCNamespace;
import org.eclipse.ecf.remoteservice.IRemoteService;
import org.eclipse.ecf.remoteservice.client.AbstractRSAClientContainer;
import org.eclipse.ecf.remoteservice.client.RemoteServiceClientRegistration;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GRPCClientContainer extends AbstractRSAClientContainer {

	private ManagedChannel channel;

	public GRPCClientContainer() {
		super(GRPCNamespace.getInstance().createInstance(new Object[] { "uuid:" + UUID.randomUUID().toString() }));
	}

	protected Method findNewStubStaticMethod(Class<?> grpcClass, String methodName)
			throws NoSuchMethodException, SecurityException {
		// Get 'newBlockingStub' static method
		return grpcClass.getMethod(methodName, io.grpc.Channel.class);
	}

	protected Class<?> loadGrpcStubClass(RemoteServiceClientRegistration registration) throws ClassNotFoundException {
		// Get grcpStubClassname from properties
		String grcpClassname = (String) registration.getProperty(GRPCConstants.GRPC_STUB_CLASS_PROP);
		// Load grcpStubClass
		return Class.forName(grcpClassname, true, this.getClass().getClassLoader());
	}

	protected ManagedChannel createChannel(String host, int port) {
		return ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected IRemoteService createRemoteService(final RemoteServiceClientRegistration registration) {
		try {
			// Get URI from connected ID
			URI uri = ((URIID) getConnectedID()).toURI();
			Method method = findNewStubStaticMethod(loadGrpcStubClass(registration),
					GRPCConstants.GRPC_STUB_METHOD_NAME);
			if (method == null) {
				throw new NoSuchMethodError("No method=" + GRPCConstants.GRPC_STUB_METHOD_NAME);
			}
			this.channel = createChannel(uri.getHost(), uri.getPort());
			// Invoke method with channel to get stub
			io.grpc.stub.AbstractStub stub = (io.grpc.stub.AbstractStub) method.invoke(null, channel);
			// Prepare a new client service
			return new GRPCClientService(this, registration, stub);
		} catch (final Exception ex) {
			// TODO: log exception
			ex.printStackTrace();
			// Return null in case of error
			return null;
		}
	}

	@Override
	public void dispose() {
		if (channel != null && !channel.isShutdown()) {
			channel.shutdown();
			channel = null;
		}
	}
}
