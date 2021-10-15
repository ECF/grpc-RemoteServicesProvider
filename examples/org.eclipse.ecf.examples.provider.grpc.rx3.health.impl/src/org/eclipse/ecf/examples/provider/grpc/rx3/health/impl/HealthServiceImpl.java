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
package org.eclipse.ecf.examples.provider.grpc.rx3.health.impl;

import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Component;

import io.grpc.health.v1.rx3.HealthCheckRequest;
import io.grpc.health.v1.rx3.HealthCheckResponse;
import io.grpc.health.v1.rx3.HealthCheckResponse.ServingStatus;
import io.grpc.health.v1.rx3.HealthCheckService;
import io.grpc.health.v1.rx3.Rx3HealthCheckGrpc;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


@Component(property = { "service.exported.interfaces=*", "service.exported.configs=ecf.grpc.server",
		"ecf.grpc.server.port=50002" })
public class HealthServiceImpl extends Rx3HealthCheckGrpc.HealthCheckImplBase implements HealthCheckService {

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
		return requests.map(HealthCheckRequest::getMessage).map(m -> {
			System.out.println("watchClient received="+m);
			return m;
		}).toList().map(names -> createServingResponse());
	}

	@Override
	public Flowable<HealthCheckResponse> watchBidi(Flowable<HealthCheckRequest> request) {
		return request.map(r -> {
			System.out.println("watchBidi request received=" + r.getMessage());
			return r;
		}).map(r -> createServingResponse());
	}
}
