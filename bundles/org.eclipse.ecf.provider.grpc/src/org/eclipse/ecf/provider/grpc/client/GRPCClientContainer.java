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
package org.eclipse.ecf.provider.grpc.client;

import java.util.Dictionary;
import java.util.Map;
import java.util.UUID;

import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.identity.URIID;
import org.eclipse.ecf.provider.grpc.identity.GRPCNamespace;
import org.eclipse.ecf.remoteservice.IRemoteService;
import org.eclipse.ecf.remoteservice.client.AbstractRSAClientContainer;
import org.eclipse.ecf.remoteservice.client.IRemoteCallable;
import org.eclipse.ecf.remoteservice.client.RemoteServiceClientRegistration;

public class GRPCClientContainer extends AbstractRSAClientContainer {

	public GRPCClientContainer() {
		super(GRPCNamespace.getInstance().createInstance(new Object[] { "uuid:" + UUID.randomUUID().toString() }));
	}

	@SuppressWarnings({ })
	@Override
	protected IRemoteService createRemoteService(final RemoteServiceClientRegistration registration) {
		return new GRPCClientService(this, registration, ((URIID) getConnectedID()).toURI());
	}

	public class GRPCClientRegistration extends RSAClientRegistration {

		public GRPCClientRegistration(ID targetID, String[] classNames, IRemoteCallable[][] restCalls,
				Dictionary<?, ?> properties) {
			super(targetID, classNames, restCalls, properties);
		}
		
		public void setClassLoader(ClassLoader cl) {
			super.setClassLoader(cl);
		}
	}
	
	@Override
	protected RemoteServiceClientRegistration createRSAClientRegistration(ID targetID, String[] interfaces,
			Map<String, Object> endpointDescriptionProperties) {
		Dictionary<?, ?> d = createRegistrationProperties(endpointDescriptionProperties);
		return new GRPCClientRegistration(targetID, interfaces, createRegistrationCallables(targetID, interfaces, d), d);
	}
}
