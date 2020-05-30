/*******************************************************************************
 * Copyright (c) 2020 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.examples.provider.grpc.health.consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;

import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckService;
import io.reactivex.Single;

@Component(immediate = true)
public class HealthServiceConsumer {

	private HealthCheckService healthService;
	
	@Reference(policy = ReferencePolicy.DYNAMIC)
	void bindHealthService(HealthCheckService hs) {
		this.healthService = hs;
	}
	
	void unbindHealthService(HealthCheckService hs) {
		this.healthService = hs;
	}
	
	void activate() {
		System.out.println("got health check response="
				+ healthService.check(Single.just(HealthCheckRequest.newBuilder().setMessage("health request service").build())).blockingGet());
	}
}
