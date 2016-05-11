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
import java.util.List;

import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.remoteservice.client.AbstractClientContainer;
import org.eclipse.ecf.remoteservice.client.AbstractRSAClientService;
import org.eclipse.ecf.remoteservice.client.RemoteServiceClientRegistration;

import io.grpc.stub.AbstractStub;

public class GRPCClientService extends AbstractRSAClientService {

	@SuppressWarnings("rawtypes")
	private final io.grpc.stub.AbstractStub blockingStub;
	private final List<Method> blockingStubMethods = new ArrayList<Method>();
	
	public GRPCClientService(AbstractClientContainer container, RemoteServiceClientRegistration registration, @SuppressWarnings("rawtypes") AbstractStub blockingStub) {
		super(container, registration);
		this.blockingStub = blockingStub;
		for(Method m: this.blockingStub.getClass().getDeclaredMethods()) 
			blockingStubMethods.add(m);
	}

	@Override
	protected Object invokeAsync(RSARemoteCall remoteCall) throws ECFException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	private boolean compareParameterTypes(Class[] first, Class[] second) {
		if (first.length != second.length) return false;
		for(int i=0; i < first.length; i++) 
			if (!first[i].equals(second[i])) return false;	
		return true;
	}
	
	@Override
	protected Object invokeSync(RSARemoteCall remoteCall) throws ECFException {
		Method rcMethod = remoteCall.getReflectMethod();
		String rcMethodName = rcMethod.getName();
		@SuppressWarnings("rawtypes")
		Class[] rcMethodParameterTypes = rcMethod.getParameterTypes();
		Method invokeMethod = null;
		for(Method m: blockingStubMethods) 
			if (rcMethodName.equals(m.getName()) && compareParameterTypes(rcMethodParameterTypes,m.getParameterTypes()))
				invokeMethod = m;
		if (invokeMethod == null) throw new ECFException("Cannot find matching invokeMethod on grpc blockingStub");
		try {
			return invokeMethod.invoke(blockingStub,remoteCall.getParameters());
		} catch (Exception e) {
			ECFException ee = new ECFException("Cannot invoke method on grcp blockingStub",e);
			ee.setStackTrace(e.getStackTrace());
			throw ee;
		}
	}

}
