/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.examples.provider.grpc.helloworld.host;

import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

import org.eclipse.ecf.osgi.services.remoteserviceadmin.DebugRemoteServiceAdminListener;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.remoteserviceadmin.RemoteServiceAdminListener;

import io.grpc.examples.helloworld.GreeterGrpc.AbstractGreeter;
import io.grpc.examples.helloworld.GreeterService;
import io.grpc.stub.StreamObserver;

@Component(immediate = true, service = GreeterService.class, property = {
		"service.exported.interfaces=io.grpc.examples.helloworld.GreeterService",
		"service.exported.configs=ecf.grpc.server" })
public class GreeterImpl extends AbstractGreeter implements GreeterService {

	@Activate
	void activate(BundleContext context) throws Exception {
		// This presents debug output of the RSA endpoint description to system out
		// So that export can be easily verified
		context.registerService(RemoteServiceAdminListener.class, new DebugRemoteServiceAdminListener(), null);
	}

	@Override
	public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
		HelloReply reply = sayHello(req);
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public HelloReply sayHello(HelloRequest req) {
		return HelloReply.newBuilder().setMessage("Hello there " + req.getName()).build();
	}
}
