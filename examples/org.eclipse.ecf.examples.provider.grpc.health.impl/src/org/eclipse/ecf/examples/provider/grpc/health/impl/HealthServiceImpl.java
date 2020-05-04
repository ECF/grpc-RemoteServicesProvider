/*******************************************************************************
 * Copyright (c) 2020 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.examples.provider.grpc.health.impl;

import org.osgi.service.component.annotations.Component;

import io.grpc.health.v1.AbstractHealthCheckServiceImpl;
import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.health.v1.HealthCheckResponse.ServingStatus;
import io.grpc.health.v1.HealthCheckService;

@Component(property = {
		"service.exported.interfaces=*",
		"service.exported.configs=ecf.grpc.server",
		"ecf.grpc.server.uriContext=http://localhost:50001" })
public class HealthServiceImpl extends AbstractHealthCheckServiceImpl implements HealthCheckService {

	@Override
	public HealthCheckResponse check(HealthCheckRequest request) {
		return HealthCheckResponse.newBuilder().setStatus(ServingStatus.SERVING).build();
	}

}
