/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc.host;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.eclipse.ecf.core.identity.URIID;
import org.eclipse.ecf.provider.grpc.GRPCConstants;
import org.eclipse.ecf.provider.grpc.identity.GRPCNamespace;
import org.eclipse.ecf.remoteservice.AbstractRSAContainer;
import org.eclipse.ecf.remoteservice.RSARemoteServiceContainerAdapter.RSARemoteServiceRegistration;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.util.MutableHandlerRegistry;

public class GRPCHostContainer extends AbstractRSAContainer {

	// shutdown timeout in milliseconds
	private static final long GRPC_SERVER_SHUTDOWN_TIMEOUT = Long
			.valueOf(System.getProperty(GRPCHostContainer.class.getName() + ".shutdownTimeout", "3000"));

	@SuppressWarnings("rawtypes")
	protected final ServerBuilder serverBuilder;

	protected Map<RSARemoteServiceRegistration, ServerServiceDefinition> registrationToDefinitionMap;
	protected MutableHandlerRegistry serviceHandlerRegistry;

	protected Server server;

	public GRPCHostContainer(URI uri) {
		super(GRPCNamespace.INSTANCE.createInstance(new Object[] { uri }));
		this.serverBuilder = ServerBuilder.forPort(((URIID) getID()).toURI().getPort());
		this.serviceHandlerRegistry = new MutableHandlerRegistry();
		this.serverBuilder.fallbackHandlerRegistry(serviceHandlerRegistry);
		this.registrationToDefinitionMap = new ConcurrentHashMap<RSARemoteServiceRegistration, ServerServiceDefinition>();
	}

	private String getGrpcStubClassname(RSARemoteServiceRegistration registration) {
		String stubClassNameFromRegistration = (String) registration.getProperty(GRPCConstants.GRPC_STUB_CLASS_PROP);
		if (stubClassNameFromRegistration != null) {
			return stubClassNameFromRegistration;// If not then get the service super class and use that
		}
		Object service = registration.getService();
		@SuppressWarnings("rawtypes")
		Class clazz = service.getClass();
		while (!Object.class.equals(clazz.getSuperclass()))
			clazz = clazz.getSuperclass();
		// This is the Grpc stub factory class class
		@SuppressWarnings("rawtypes")
		Class enclosingClass = clazz.getEnclosingClass();
		return (enclosingClass != null) ? enclosingClass.getName() : clazz.getName();
	}

	@Override
	protected Map<String, Object> exportRemoteService(RSARemoteServiceRegistration registration) {
		Map<String, Object> newProps = new HashMap<String, Object>();
		try {
			// get grpc stub classname from registration or from service class
			String grpcStubClassname = getGrpcStubClassname(registration);
			// get service from registration
			Object service = registration.getService();
			ServerServiceDefinition serviceDefinition = null;
			synchronized (this) {
				// If server is null we add the service to the service builder, build, and start
				if (this.server == null) {
					this.serverBuilder.addService((BindableService) service);
					this.server = serverBuilder.build();
					this.server.start();
					// There should be only one service add at this point
					serviceDefinition = this.server.getServices().iterator().next();
				} else {
					// Add the service via the serviceHandlerRegistry
					serviceDefinition = this.serviceHandlerRegistry.addService((BindableService) service);
				}
				// put registration -> serviceDefinition in map so can be used to unexport
				this.registrationToDefinitionMap.put(registration, serviceDefinition);
			}
			// Put the grpc stub classname into the properties
			newProps.put(GRPCConstants.GRPC_STUB_CLASS_PROP, grpcStubClassname);
		} catch (Exception e) {
			RuntimeException e1 = new RuntimeException("Could not export with grpc provider", e);
			e1.setStackTrace(e.getStackTrace());
			throw e1;
		}
		return newProps;
	}

	@Override
	protected void unexportRemoteService(RSARemoteServiceRegistration registration) {
		Server server = null;
		synchronized (this) {
			server = this.server;
			if (server != null) {
				ServerServiceDefinition serviceDefinition = this.registrationToDefinitionMap.remove(registration);
				if (serviceDefinition != null) {
					this.serviceHandlerRegistry.removeService(serviceDefinition);
				}
			}
		}
	}

	protected void shutdownServer(long timeoutInSeconds) {
		if (server != null) {
			if (!server.isShutdown()) {
				server.shutdown();
				try {
					server.awaitTermination(timeoutInSeconds, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
				}
				if (!server.isTerminated()) {
					server.shutdownNow();
				}
				server = null;
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		shutdownServer(GRPC_SERVER_SHUTDOWN_TIMEOUT);
	}
}
