/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc;

import java.net.URI;
import java.util.Map;

import org.eclipse.ecf.core.ContainerTypeDescription;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.identity.Namespace;
import org.eclipse.ecf.provider.grpc.client.GRPCClientContainer;
import org.eclipse.ecf.provider.grpc.host.GRPCHostContainer;
import org.eclipse.ecf.provider.grpc.identity.GRPCNamespace;
import org.eclipse.ecf.remoteservice.provider.IRemoteServiceDistributionProvider;
import org.eclipse.ecf.remoteservice.provider.RemoteServiceContainerInstantiator;
import org.eclipse.ecf.remoteservice.provider.RemoteServiceDistributionProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		// Register GRPCNamespace
		context.registerService(Namespace.class, new GRPCNamespace(), null);
		context.registerService(IRemoteServiceDistributionProvider.class,
				new RemoteServiceDistributionProvider.Builder().setName(GRPCConstants.SERVER_PROVIDER_CONFIG_TYPE)
						.setInstantiator(new RemoteServiceContainerInstantiator(
								GRPCConstants.SERVER_PROVIDER_CONFIG_TYPE, GRPCConstants.CLIENT_PROVIDER_CONFIG_TYPE) {
							@Override
							public IContainer createInstance(ContainerTypeDescription description,
									Map<String, ?> parameters) {
								int serverPort = getParameterValue(parameters, GRPCConstants.SERVER_PORT, Integer.class,
										Integer.valueOf(GRPCConstants.SERVER_PORT_DEFAULT));
								String hostname = getParameterValue(parameters, GRPCConstants.SERVER_HOSTNAME,
										GRPCConstants.SERVER_HOSTNAME_DEFAULT);
								return new GRPCHostContainer(URI.create(
										GRPCNamespace.INSTANCE.getScheme() + "://" + hostname + ":" + serverPort));
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
