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
package org.eclipse.ecf.examples.provider.grpc.rx3.health.consumer;

import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import io.grpc.health.v1.rx3.HealthCheckRequest;
import io.grpc.health.v1.rx3.HealthCheckService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

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
