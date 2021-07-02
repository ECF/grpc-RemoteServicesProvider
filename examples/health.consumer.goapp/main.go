/*******************************************************************************
 * Copyright (c) 2021 Martin Schemel and Composent, Inc. and others. All rights
 * reserved. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Martin Schemel and Composent, Inc. - initial API and implementation
 ******************************************************************************/

package main

import (
	"context"
	"fmt"
	"time"

	"google.golang.org/grpc"

	healthCheck "github.com/ECF/grpc-RemoteServicesProvider/health-consumer-goapp/proto"
)

const (
	hostname = "localhost"
	port     = "50002"
)

func main() {
	fmt.Println("HealthCheck service at hostname=" + hostname + " port=" + port)

	// Establish a client connection
	conn, err := grpc.Dial(hostname+":"+port, grpc.WithInsecure(), grpc.WithBlock())
	if err != nil {
		fmt.Println("did not connect: " + err.Error())
	}
	defer conn.Close()

	ctx, cancel := context.WithTimeout(context.Background(), time.Second)
	defer cancel()

	healthCheckClient := healthCheck.NewHealthCheckClient(conn)

	// Make check method call
	fmt.Print("Calling check method...")
	var r *healthCheck.HealthCheckResponse
	r, err = healthCheckClient.Check(ctx, &healthCheck.HealthCheckRequest{Message: ""})

	if err != nil {
		fmt.Println("could not check health: " + err.Error())
	}

	// Print status from health check response
	fmt.Printf("returned status=%s", r.GetStatus())
}
