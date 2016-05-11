/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.examples.provider.grpc.helloworld.consumer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;

import io.grpc.examples.helloworld.GreeterService;
import io.grpc.examples.helloworld.HelloRequest;

@Component(immediate = true)
public class GreeterComponent {

	private GreeterService greeterService;

	@Reference(policy = ReferencePolicy.DYNAMIC)
	void bindGreeterService(GreeterService greeter) {
		this.greeterService = greeter;
	}

	void unbindGreeterService(GreeterService greeter) {
		this.greeterService = null;
	}

	@Activate
	void activate() throws Exception {
		System.out.println("Calling Greeter Service");
		System.out.println("Greeter service reply message="
				+ this.greeterService.sayHello(HelloRequest.newBuilder().setName("Scott").build()).getMessage());
	}
}
