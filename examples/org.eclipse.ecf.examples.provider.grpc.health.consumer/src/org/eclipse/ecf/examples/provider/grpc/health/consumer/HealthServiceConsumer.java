/*******************************************************************************
 * Copyright (c) 2020 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.examples.provider.grpc.health.consumer;

import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckService;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Component(immediate = true)
public class HealthServiceConsumer {

	@Reference
	private HealthCheckService healthService;

	private Single<HealthCheckRequest> getSingle(String message) {
		return Single.just(HealthCheckRequest.newBuilder().setMessage(message).build());
	}

	private Flowable<HealthCheckRequest> getRequestFlowable(int count, String message) {
		HealthCheckRequest[] requests = new HealthCheckRequest[count];
		for (int i = 0; i < count; i++) {
			requests[i] = HealthCheckRequest.newBuilder().setMessage(message + " #" + String.valueOf(i)).build();
		}
		return Flowable.fromArray(requests).delay(500,TimeUnit.MILLISECONDS);
	}

	void activate() {
		// test check
		healthService.check(getSingle("check client message")).subscribe(resp -> {
			System.out.println("check response=" + resp.getStatus());
		});
		// Test watchServer: single request, multiple server responses
		healthService.watchServer(getSingle("watchServer client message")).subscribe(resp -> {
			System.out.println("watchServer received=" + resp.getStatus());
		});

		// Test watchClient: multiple client requests, single server response
		healthService.watchClient(getRequestFlowable(40, "watchClient client message")).subscribe(resp -> {
			System.out.println("watchClient response=" + resp.getStatus());
		});

		// Test watchBidi: multiple client requests, multiple server responses
		// Make flowable of 30 requests for watchClient
		healthService.watchBidi(getRequestFlowable(30, "watchBidi client message")).subscribe(resp -> {
			System.out.println("watchBidi received=" + resp.getStatus());
		});
	}
}
