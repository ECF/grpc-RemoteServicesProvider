/*******************************************************************************
 * Copyright (c) 2020 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.examples.provider.grpc.health.impl;

import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Component;

import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.health.v1.HealthCheckResponse.ServingStatus;
import io.grpc.health.v1.HealthCheckService;
import io.grpc.health.v1.RxHealthCheckGrpc;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Component(property = { "service.exported.interfaces=*", "service.exported.configs=ecf.grpc.server",
		"ecf.grpc.server.port=50002" })
public class HealthServiceImpl extends RxHealthCheckGrpc.HealthCheckImplBase implements HealthCheckService {

	private HealthCheckResponse createServingResponse() {
		return HealthCheckResponse.newBuilder().setStatus(ServingStatus.SERVING).build();
	}

	@Override
	public Single<HealthCheckResponse> check(Single<HealthCheckRequest> request) {
		return request.map(r -> {
			System.out.println("check received=" + r.getMessage());
			return r;
		}).map(r -> createServingResponse());
	}

	@Override
	public Flowable<HealthCheckResponse> watchServer(Single<HealthCheckRequest> request) {
		return request.map(r -> {
			System.out.println("watchServer received=" + r.getMessage());
			return r;
		}).toFlowable().flatMap(r -> {
			// Send 30 responses for each request
			HealthCheckResponse[] responses = new HealthCheckResponse[30];
			for (int i = 0; i < 30; i++) {
				responses[i] = createServingResponse();
			}
			// delay one second between each response
			return Flowable.fromArray(responses).delay(500, TimeUnit.MILLISECONDS);
		}).map(r -> createServingResponse());
	}

	@Override
	public Single<HealthCheckResponse> watchClient(Flowable<HealthCheckRequest> requests) {
		requests.subscribe(request -> {
			System.out.println("watchClient received=" + request.getMessage());
		});
		return Single.just(createServingResponse());
	}

	@Override
	public Flowable<HealthCheckResponse> watchBidi(Flowable<HealthCheckRequest> request) {
		return request.map(r -> {
			System.out.println("watchBidi request received=" + r.getMessage());
			return r;
		}).map(r -> createServingResponse());
	}
}
