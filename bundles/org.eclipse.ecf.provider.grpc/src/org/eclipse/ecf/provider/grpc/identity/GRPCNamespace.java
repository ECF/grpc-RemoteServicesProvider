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

	@Override
	public String getScheme() {
		return "grpc";
	}
}
