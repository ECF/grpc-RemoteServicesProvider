/*******************************************************************************
 * Copyright (c) 2021 Martin Schemel and Composent, Inc. and others. All rights 
 * reserved. This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which accompanies this 
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Martin Schemel and Composent, Inc. - initial API and implementation
 ******************************************************************************/
package healthcheck.consumer.javaapp;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.health.v1.HealthCheckGrpc;
import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse;

public class Main {

	public static void main(String[] args) {

		String hostname = "localhost";
		int port = 50002;

		if (args.length > 0) {
			port = Integer.valueOf(args[0]);
		}
		System.out.println("HealthCheck service at hostname=" + hostname + " port="
				+ String.valueOf(port));
		// Create channel
		ManagedChannel channel = ManagedChannelBuilder.forAddress(hostname, port).usePlaintext().build();
		// Create client for channel
		HealthCheckGrpc.HealthCheckBlockingStub client = HealthCheckGrpc.newBlockingStub(channel);
		// Make check method call
		System.out.print("Calling check method...");
		final HealthCheckResponse check = client.check(HealthCheckRequest.newBuilder().build());
		// Print status from health check response
		System.out.println("returned status=" + check.getStatus());
		// Shutdown channel
		channel.shutdown();
	}
}