/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.examples.provider.grpc.helloworld.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import io.grpc.examples.helloworld.GreeterService;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

public class Activator implements BundleActivator, ServiceTrackerCustomizer<GreeterService,GreeterService> {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	ServiceTracker<GreeterService,GreeterService> st;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		st = new ServiceTracker<GreeterService,GreeterService>(context,GreeterService.class,this);
		st.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		if (st != null) {
			st.close();
			st = null;
		}
		Activator.context = null;
	}

	@Override
	public GreeterService addingService(ServiceReference<GreeterService> reference) {
		GreeterService svc = getContext().getService(reference);
		// Call!
		HelloReply reply = svc.sayHello(HelloRequest.newBuilder().setName("Scott").build());
		System.out.println("Hello reply="+reply.getMessage());
		return svc;
	}

	@Override
	public void modifiedService(ServiceReference<GreeterService> reference, GreeterService service) {
	}

	@Override
	public void removedService(ServiceReference<GreeterService> reference, GreeterService service) {
	}

}
