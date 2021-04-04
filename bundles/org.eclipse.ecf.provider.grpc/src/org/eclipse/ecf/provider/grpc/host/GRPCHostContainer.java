/*******************************************************************************
 * Copyright (c) 2021 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc.host;

import java.io.IOException;
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
import io.grpc.protobuf.services.ProtoReflectionService;
import io.grpc.util.MutableHandlerRegistry;

public class GRPCHostContainer extends AbstractRSAContainer {

	protected final Map<RSARemoteServiceRegistration, ServerServiceDefinition> registrationToDefinitionMap;
	protected final MutableHandlerRegistry serviceHandlerRegistry;

	protected Server server;
	protected final long stopTimeout;
	
	public GRPCHostContainer(URI uri, long stopTimeout, boolean serverReflection) {
		super(GRPCNamespace.INSTANCE.createInstance(new Object[] { uri }));
		ServerBuilder<?> serverBuilder = ServerBuilder.forPort(((URIID) getID()).toURI().getPort());
		this.serviceHandlerRegistry = new MutableHandlerRegistry();
		serverBuilder.fallbackHandlerRegistry(serviceHandlerRegistry);
		this.registrationToDefinitionMap = new ConcurrentHashMap<RSARemoteServiceRegistration, ServerServiceDefinition>();
		this.stopTimeout = stopTimeout;
		if (serverReflection) {
			serverBuilder.addService(ProtoReflectionService.newInstance());
		}
		initializeServer(serverBuilder);
	}

	protected void initializeServer(ServerBuilder<?> serverBuilder) {
		this.server = serverBuilder.build();
	}
	
	public void startGrpcServer() throws IOException {
		synchronized (this) {
			if (!server.isShutdown()) {
				this.server.start();
			}
		}
	}
	
	protected void shutdownGrpcServer() {
		synchronized (this) {
			if (!server.isShutdown()) {
				server.shutdown();
				try {
					server.awaitTermination(stopTimeout, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
				}
				if (!server.isTerminated()) {
					server.shutdownNow();
				}
			}
		}
	}
	
	protected boolean addMutableService(RSARemoteServiceRegistration registration) {
		Object service = registration.getService();
		if (service instanceof BindableService) {
			BindableService bs = (BindableService) service;
			synchronized (this) {
				if (!this.server.isShutdown()) {
						ServerServiceDefinition serviceDef = bs.bindService();
						this.serviceHandlerRegistry.addService(serviceDef);
						this.registrationToDefinitionMap.put(registration, serviceDef);
						return true;
				}
			}
		}
		return false;
	}
	
	protected boolean removeMutableService(RSARemoteServiceRegistration registration) {
		synchronized (this) {
			if (!this.server.isShutdown()) {
				ServerServiceDefinition serviceDefinition = this.registrationToDefinitionMap.remove(registration);
				if (serviceDefinition != null) {
					return this.serviceHandlerRegistry.removeService(serviceDefinition);
				}
			}
		}
		return false;
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
		if (!addMutableService(registration)) {
			throw new RuntimeException(
					"Failed to add remote service with registration=" + registration + " to grpc server=" + this.server);
		}
		Map<String, Object> newProps = new HashMap<String, Object>();
		// Put the grpc stub classname into the properties
		newProps.put(GRPCConstants.GRPC_STUB_CLASS_PROP, getGrpcStubClassname(registration));
		return newProps;
	}

	@Override
	protected void unexportRemoteService(RSARemoteServiceRegistration registration) {
		removeMutableService(registration);
	}

	@Override
	public void dispose() {
		super.dispose();
		shutdownGrpcServer();
	}
}
