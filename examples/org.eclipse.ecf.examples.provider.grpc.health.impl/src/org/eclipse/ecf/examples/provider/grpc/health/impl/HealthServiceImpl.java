/*******************************************************************************
 * Copyright (c) 2020 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.examples.provider.grpc.health.impl;

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

	private Single<HealthCheckResponse> getSingle() {
		return Single.just(HealthCheckResponse.newBuilder().setStatus(ServingStatus.SERVING).build());
	}

	@Override
	public Single<HealthCheckResponse> check(Single<HealthCheckRequest> request) {
		return getSingle();
	}

	private Flowable<HealthCheckResponse> getResponseFlowable(int count) {
		HealthCheckResponse[] responses = new HealthCheckResponse[count];
		for (int i = 0; i < count; i++) {
			responses[i] = HealthCheckResponse.newBuilder().setStatus(ServingStatus.SERVING).build();
		}
		return Flowable.fromArray(responses);
	}

	@Override
	public io.reactivex.Flowable<io.grpc.health.v1.HealthCheckResponse> watchServer(
			io.reactivex.Single<io.grpc.health.v1.HealthCheckRequest> request) {
		return getResponseFlowable(30);
	}

	@Override
	public io.reactivex.Single<io.grpc.health.v1.HealthCheckResponse> watchClient(
			io.reactivex.Flowable<io.grpc.health.v1.HealthCheckRequest> request) {
		request.subscribe(c -> {
			System.out.println("watchClient received=" + c.getMessage());
		});
		return getSingle();
	}

	@Override
	public Flowable<HealthCheckResponse> watchBidi(Flowable<HealthCheckRequest> request) {
		request.subscribe(c -> {
			System.out.println("watchBidi received=" + c.getMessage());
		});
		return getResponseFlowable(50);
	}
}
