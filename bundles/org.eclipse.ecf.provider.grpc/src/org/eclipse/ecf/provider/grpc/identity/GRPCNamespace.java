/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc.identity;

import org.eclipse.ecf.core.identity.URIID.URIIDNamespace;
import org.eclipse.ecf.provider.grpc.GRPCConstants;

public class GRPCNamespace extends URIIDNamespace {

	private static final long serialVersionUID = -1793395450322816274L;
	public static GRPCNamespace INSTANCE;

	public GRPCNamespace() {
		super(GRPCConstants.NAMESPACE_NAME, "Google RPC Namespace");
		INSTANCE = this;
	}

	public static GRPCNamespace getInstance() {
		return INSTANCE;
	}

	public String getScheme() {
		return "grpc";
	}
}
