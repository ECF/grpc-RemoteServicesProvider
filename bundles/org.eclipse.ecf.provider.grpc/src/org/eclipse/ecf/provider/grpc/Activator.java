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
package org.eclipse.ecf.provider.grpc;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerTypeDescription;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.identity.Namespace;
import org.eclipse.ecf.core.util.BundleStarter;
import org.eclipse.ecf.provider.grpc.client.GRPCClientContainer;
import org.eclipse.ecf.provider.grpc.host.GRPCHostContainer;
import org.eclipse.ecf.provider.grpc.identity.GRPCNamespace;
import org.eclipse.ecf.remoteservice.provider.IRemoteServiceDistributionProvider;
import org.eclipse.ecf.remoteservice.provider.RemoteServiceContainerInstantiator;
import org.eclipse.ecf.remoteservice.provider.RemoteServiceDistributionProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final String[] GRPC_BUNDLES_TO_ACTIVATE = System
			.getProperty("org.eclipse.ecf.provider.grpc.bundlesToActivate", "org.apache.aries.spifly.dynamic.bundle")
			.split(",");

	@Override
	public void start(BundleContext context) throws Exception {
		// Register GRPCNamespace
		BundleStarter.startDependents(context, GRPC_BUNDLES_TO_ACTIVATE, Bundle.RESOLVED);
		context.registerService(Namespace.class, new GRPCNamespace(), null);
		context.registerService(IRemoteServiceDistributionProvider.class,
				new RemoteServiceDistributionProvider.Builder().setName(GRPCConstants.SERVER_PROVIDER_CONFIG_TYPE)
						.setInstantiator(new RemoteServiceContainerInstantiator(
								GRPCConstants.SERVER_PROVIDER_CONFIG_TYPE, GRPCConstants.CLIENT_PROVIDER_CONFIG_TYPE) {
							@Override
							public IContainer createInstance(ContainerTypeDescription description,
									Map<String, ?> parameters) throws ContainerCreateException {
								String serverPort = getParameterValue(parameters, GRPCConstants.SERVER_PORT,
										String.class, GRPCConstants.SERVER_PORT_DEFAULT);
								String hostname = getParameterValue(parameters, GRPCConstants.SERVER_HOSTNAME,
										GRPCConstants.SERVER_HOSTNAME_DEFAULT);
								String stopTimeout = getParameterValue(parameters, GRPCConstants.SERVER_STOP_TIMEOUT,
										GRPCConstants.SERVER_STOP_TIMEOUT_DEFAULT);
								String protoReflectionService = getParameterValue(parameters,
										GRPCConstants.SERVER_PROTOREFLECTIONSERVICE,
										GRPCConstants.SERVER_PROTOREFLECTIONSERVICE_DEFAULT);
								GRPCHostContainer c = new GRPCHostContainer(
										URI.create(GRPCNamespace.INSTANCE.getScheme() + "://" + hostname + ":"
												+ serverPort),
										Long.valueOf(stopTimeout), Boolean.valueOf(protoReflectionService));
								try {
									c.startGrpcServer();
									return c;
								} catch (IOException e) {
									throw new ContainerCreateException(
											"Cannot start grpc server hostname=" + hostname + ",port=" + serverPort, e);
								}
							}
						}).setServer(true).setHidden(false).build(),
				null);
		context.registerService(IRemoteServiceDistributionProvider.class,
				new RemoteServiceDistributionProvider.Builder().setName(GRPCConstants.CLIENT_PROVIDER_CONFIG_TYPE)
						.setInstantiator(new RemoteServiceContainerInstantiator(
								GRPCConstants.SERVER_PROVIDER_CONFIG_TYPE, GRPCConstants.CLIENT_PROVIDER_CONFIG_TYPE) {
							@Override
							public IContainer createInstance(ContainerTypeDescription description,
									Map<String, ?> parameters) {
								return new GRPCClientContainer();
							}
						}).setServer(false).setHidden(false).build(),
				null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

}
