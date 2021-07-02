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
