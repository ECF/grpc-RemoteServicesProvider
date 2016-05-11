/*******************************************************************************
 * Copyright (c) 2016 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc.client;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.UUID;

import org.eclipse.ecf.core.identity.URIID;
import org.eclipse.ecf.provider.grpc.GRPCConstants;
import org.eclipse.ecf.provider.grpc.identity.GRPCNamespace;
import org.eclipse.ecf.remoteservice.IRemoteService;
import org.eclipse.ecf.remoteservice.client.AbstractRSAClientContainer;
import org.eclipse.ecf.remoteservice.client.RemoteServiceClientRegistration;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GRPCClientContainer extends AbstractRSAClientContainer {

	private ManagedChannel channel;
	
	public GRPCClientContainer() {
		super(GRPCNamespace.getInstance()
				.createInstance(new Object[] { "uuid:" + UUID.randomUUID().toString() }));
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    protected IRemoteService createRemoteService(
            final RemoteServiceClientRegistration aRegistration) {
        try {
        	// Get URI from connected ID
        	URI uri = ((URIID) getConnectedID()).toURI();
    		channel = ManagedChannelBuilder.forAddress(uri.getHost(), uri.getPort())
    	            .usePlaintext(true)
    	            .build();
    		// Get grcpClassname from properties
    		String grcpClassname = (String) aRegistration.getProperty(GRPCConstants.GRCP_CLASSNAME_PROP);
    		// Load grcpClass
    		Class grpcClass = Class.forName(grcpClassname,true, this.getClass().getClassLoader());
    		// Get 'newBlockingStub' static method
    		Method method = grpcClass.getMethod("newBlockingStub",io.grpc.Channel.class);
    		// Invoke method with channel to get stub
    		io.grpc.stub.AbstractStub stub = (io.grpc.stub.AbstractStub) method.invoke(null,channel);
    		 // Prepare a new client service
            return new GRPCClientService(this, aRegistration, stub);
        } catch (final Exception ex) {
            // TODO: log exception
            ex.printStackTrace();
            // Return null in case of error
            return null;
        }
    }


    public void dispose() {
    	if (channel != null && !channel.isShutdown()) {
    		channel.shutdown();
    		channel = null;
    	}
    }
}
