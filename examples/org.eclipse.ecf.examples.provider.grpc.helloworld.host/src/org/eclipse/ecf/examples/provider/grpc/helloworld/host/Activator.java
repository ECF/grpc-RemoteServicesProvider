/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.examples.provider.grpc.helloworld.host;

import java.util.Hashtable;

import org.eclipse.ecf.osgi.services.remoteserviceadmin.DebugRemoteServiceAdminListener;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.remoteserviceadmin.RemoteServiceAdminListener;

public class Activator implements BundleActivator {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(BundleContext context) throws Exception {
		context.registerService(RemoteServiceAdminListener.class,
				new DebugRemoteServiceAdminListener(), null);
		Hashtable ht = new Hashtable();
		ht.put("service.exported.interfaces","io.grpc.examples.helloworld.GreeterService");
		ht.put("service.exported.configs","ecf.grpc.server");
		context.registerService(io.grpc.examples.helloworld.GreeterService.class,new GreeterImpl(), ht );
		System.out.println("greeter service registered");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

}
