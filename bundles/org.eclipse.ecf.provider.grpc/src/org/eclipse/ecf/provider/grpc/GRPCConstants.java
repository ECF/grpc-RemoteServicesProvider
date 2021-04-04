/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc;

public interface GRPCConstants {

	public static final String GRPC_STUB_METHOD_NAME = "newRxStub";

	public static final String NAMESPACE_NAME = "ecf.namespace.grpc";
	public static final String SERVER_PROVIDER_CONFIG_TYPE = "ecf.grpc.server";
	public static final String CLIENT_PROVIDER_CONFIG_TYPE = "ecf.grpc.client";
	public static final String SERVER_HOSTNAME = "serverHostname";
	public static final String SERVER_HOSTNAME_DEFAULT = "localhost";
	public static final String SERVER_PORT = "port";
	public static final String SERVER_PORT_DEFAULT = "50021";
	public static final String SERVER_STOP_TIMEOUT = "stopTimeout";
	public static final String SERVER_STOP_TIMEOUT_DEFAULT = System
			.getProperty(SERVER_PROVIDER_CONFIG_TYPE + "." + SERVER_STOP_TIMEOUT, "5000");
	public static final String GRPC_STUB_CLASS_PROP = "ecf.grpc.stub.class";
	public static final String SERVER_PROTOREFLECTIONSERVICE = "protoReflectionService";
	public static final String SERVER_PROTOREFLECTIONSERVICE_DEFAULT = System
			.getProperty(SERVER_PROVIDER_CONFIG_TYPE + "." + SERVER_PROTOREFLECTIONSERVICE, "false");
}
