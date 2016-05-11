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

	public static final String NAMESPACE_NAME = "ecf.namespace.grpc";
	public static final String SERVER_PROVIDER_CONFIG_TYPE = "ecf.grpc.server";
	public static final String CLIENT_PROVIDER_CONFIG_TYPE = "ecf.grpc.client";
	public static final String SERVER_SVCPROP_URICONTEXT = "uriContext";
	public static final String SERVER_DEFAULT_URICONTEXT = "http://localhost:50051";
	public static final String GRCP_CLASSNAME_PROP = "ecf.grcp.classname";

}
