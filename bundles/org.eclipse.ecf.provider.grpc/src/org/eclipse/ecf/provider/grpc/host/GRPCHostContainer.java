/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc.host;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ecf.core.identity.URIID;
import org.eclipse.ecf.provider.grpc.GRPCConstants;
import org.eclipse.ecf.provider.grpc.identity.GRPCNamespace;
import org.eclipse.ecf.remoteservice.AbstractRSAContainer;
import org.eclipse.ecf.remoteservice.RSARemoteServiceContainerAdapter.RSARemoteServiceRegistration;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GRPCHostContainer extends AbstractRSAContainer {

	@SuppressWarnings("rawtypes")
	private final ServerBuilder serverBuilder;
	private Server server;
	
	public GRPCHostContainer(String idString) {
		super(GRPCNamespace.INSTANCE.createInstance(new Object[] { idString }));
		this.serverBuilder = ServerBuilder.forPort(((URIID) getID()).toURI().getPort());
	}

	@Override
	protected Map<String, Object> exportRemoteService(RSARemoteServiceRegistration registration) {
		String grpcClassname = null;
		try {
			Object service = registration.getService();
			@SuppressWarnings("rawtypes")
			Class superClass = service.getClass();
			while (!Object.class.equals(superClass.getSuperclass())) 
				superClass = superClass.getSuperclass();
			// This is the Grpc class
			@SuppressWarnings("rawtypes")
			Class enclosingClass = superClass.getEnclosingClass();
			grpcClassname = (enclosingClass != null)?enclosingClass.getName():superClass.getName();
			// Actually create and start the server
			this.server = serverBuilder.addService((BindableService) service).build();
			this.server.start();
		} catch (Exception e) {
			RuntimeException e1 = new RuntimeException("Could not export with grpc provider",e);
			e1.setStackTrace(e.getStackTrace());
			throw e1;
		}
		Map<String,Object> newProps = new HashMap<String,Object>();
		newProps.put(GRPCConstants.GRCP_CLASSNAME_PROP,grpcClassname);
		return newProps;
	}

	@Override
	protected void unexportRemoteService(RSARemoteServiceRegistration registration) {
		if (server != null) {
			server.shutdownNow();
			server = null;
		}
	}

}
