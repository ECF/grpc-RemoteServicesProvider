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
			requests[i] = HealthCheckRequest.newBuilder().setMessage(message).build();
		}
		return Flowable.fromArray(requests);
	}

	void activate() {
		// test blocking check call
		System.out.println(
				"check response=" + healthService.check(getSingle("check client message")).blockingGet().getStatus());
		// test watchServer: single request, multiple server responses
		healthService.watchServer(getSingle("watchServer client message")).subscribe(r -> {
			System.out.println("watchServer received=" + r.getStatus());
		});
		// test watchClient: multiple client messages, single server response
		System.out.println("watchClient response=" + healthService
				.watchClient(getRequestFlowable(40, "watchClient client message")).blockingGet().getStatus());
		// test watchBidi: multiple client messages, multiple server responses
		healthService.watchBidi(getRequestFlowable(30, "watchBidi client message")).subscribe(r -> {
			System.out.println("watchBidi received=" + r.getStatus());
		});
	}
}
