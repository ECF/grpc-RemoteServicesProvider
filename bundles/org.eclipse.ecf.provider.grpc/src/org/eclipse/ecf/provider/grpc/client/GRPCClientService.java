/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc.client;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.remoteservice.client.AbstractClientContainer;
import org.eclipse.ecf.remoteservice.client.AbstractRSAClientService;
import org.eclipse.ecf.remoteservice.client.RemoteServiceClientRegistration;
import org.eclipse.equinox.concurrent.future.IExecutor;
import org.eclipse.equinox.concurrent.future.IProgressRunnable;

import io.grpc.stub.AbstractStub;

public class GRPCClientService extends AbstractRSAClientService {

	@SuppressWarnings("rawtypes")
	private final io.grpc.stub.AbstractStub stub;
	private final List<Method> stubMethods = new ArrayList<Method>();

	public GRPCClientService(AbstractClientContainer container, RemoteServiceClientRegistration registration,
			@SuppressWarnings("rawtypes") AbstractStub blockingStub) {
		super(container, registration);
		this.stub = blockingStub;
		Arrays.asList(blockingStub.getClass().getDeclaredMethods()).forEach(m -> stubMethods.add(m));
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
			throw new ECFException("Cannot find matching invokeMethod on grpc stub="+stub);
		}
		return optInvokeMethod.get();
	}
	
	@Override
	protected Object invokeSync(RSARemoteCall remoteCall) throws ECFException {
		try {
			 return findInvokeMethod(remoteCall).invoke(stub, remoteCall.getParameters());
		} catch (Exception e) {
			ECFException ee = new ECFException("Cannot invoke method on grcp stub="+stub, e);
			ee.setStackTrace(e.getStackTrace());
			throw ee;
		}
	}

}
