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

	protected final Server server;
	protected final long stopTimeout;
	protected final Map<RSARemoteServiceRegistration, ServerServiceDefinition> registrationToDefinitionMap;
	protected final MutableHandlerRegistry serviceHandlerRegistry;

	protected ServerServiceDefinition protoReflectionServiceDef;

	public GRPCHostContainer(URI uri, long stopTimeout, boolean serverReflection) {
		super(GRPCNamespace.INSTANCE.createInstance(new Object[] { uri }));
		ServerBuilder<?> serverBuilder = ServerBuilder.forPort(((URIID) getID()).toURI().getPort());
		this.serviceHandlerRegistry = new MutableHandlerRegistry();
		serverBuilder.fallbackHandlerRegistry(serviceHandlerRegistry);
		this.registrationToDefinitionMap = new ConcurrentHashMap<RSARemoteServiceRegistration, ServerServiceDefinition>();
		this.stopTimeout = stopTimeout;
		this.server = initialize(serverBuilder);
		if (serverReflection) {
			this.protoReflectionServiceDef = ProtoReflectionService.newInstance().bindService();
		}
	}

	protected Server initialize(ServerBuilder<?> serverBuilder) {
		return serverBuilder.build();
	}

	public boolean isServerShutdown() {
		synchronized (this.server) {
			return this.server.isShutdown();
		}
	}

	public boolean isServerTerminated() {
		synchronized (this.server) {
			return this.server.isTerminated();
		}
	}

	public boolean addProtoReflectionServiceDef() {
		synchronized (this.server) {
			if (!isServerShutdown()) {
				if (this.protoReflectionServiceDef == null) {
					this.protoReflectionServiceDef = ProtoReflectionService.newInstance().bindService();
				}
				this.serviceHandlerRegistry.addService(this.protoReflectionServiceDef);
				return true;
			}
		}
		return false;
	}

	public boolean removeProtoReflectionServiceDef() {
		synchronized (this.server) {
			if (!isServerShutdown()) {
				ServerServiceDefinition serviceDefinition = this.protoReflectionServiceDef;
				if (serviceDefinition != null) {
					boolean success = this.serviceHandlerRegistry.removeService(serviceDefinition);
					if (success) {
						this.protoReflectionServiceDef = null;
					}
					return success;
				}
			}
		}
		return false;
	}

	public boolean isProtoReflectionServiceDef() {
		synchronized (this.server) {
			return this.protoReflectionServiceDef != null;
		}
	}

	public boolean toggleProtoReflectionServiceDef() {
		synchronized (this.server) {
			return (!isProtoReflectionServiceDef()) ? addProtoReflectionServiceDef()
					: removeProtoReflectionServiceDef();
		}
	}

	public void startGrpcServer() throws IOException {
		synchronized (this.server) {
			if (!isServerShutdown()) {
				this.server.start();
				if (this.protoReflectionServiceDef != null) {
					this.serviceHandlerRegistry.addService(this.protoReflectionServiceDef);
				}
			}
		}
	}

	protected void shutdownGrpcServer() {
		synchronized (this.server) {
			if (!isServerShutdown()) {
				server.shutdown();
				try {
					server.awaitTermination(stopTimeout, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
				}
				if (!isServerTerminated()) {
					server.shutdownNow();
				}
			}
		}
	}

	protected boolean addMutableService(RSARemoteServiceRegistration registration) {
		synchronized (this.server) {
			if (!isServerShutdown()) {
				Object service = registration.getService();
				if (service instanceof BindableService) {
					BindableService bs = (BindableService) service;
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
		synchronized (this.server) {
			if (!isServerShutdown()) {
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
			throw new RuntimeException("Failed to add remote service with registration=" + registration
					+ " to grpc server=" + this.server);
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
