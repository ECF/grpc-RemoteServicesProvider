# grpc-RemoteServicesProvider
This project provides an [ECF Remote Services](https://wiki.eclipse.org/ECF) [Distribution Provider](https://wiki.eclipse.org/Distribution_Providers) based upon [Google RPC for Java (grpc-java)](https://github.com/grpc/grpc-java).   See http://www.grpc.io, https://github.com/grpc, and https://github.com/grpc/grpc-java for information on grpc-java, and https://wiki.eclipse.org/ECF for information about ECF's implentation of OSGi Remote Services.

This distribution provider uses grpc-java to export and import OSGi Remote Services and make them available for remote access as full OSGi services; with all of the dynamics, versioning, security, management, extensibility, and other features that come with the [OSGi Remote Services](https://docs.osgi.org/specification/osgi.cmpn/7.0.0/service.remoteservices.html) and [OSGi Remote Service Admin specification](https://docs.osgi.org/specification/osgi.cmpn/7.0.0/service.remoteserviceadmin.html).   

## OSGi Remote Service API

OSGi Remote Services are typically declared by a Java interface representing the service contract.  This service interface (and any classes referenced by this interface) is created by the programmer to match the desired call-return semantics of the service.  The service interface and referenced classes represent the **service api**.   

An implementation of this interface is typically created by the programmer and exported at runtime via one or more distribution providers.   

See [here](https://wiki.eclipse.org/Tutorial:_Building_your_first_OSGi_Remote_Service) for a short tutorial describing how to [Create the Remote Service](https://wiki.eclipse.org/Tutorial:_Building_your_first_OSGi_Remote_Service#Common_to_Host_and_Consumer:_Create_the_Service_Interface) and [Implement the Service](https://wiki.eclipse.org/Tutorial:_Building_your_first_OSGi_Remote_Service#Service_Host:_Implement_the_Service).

In OSGi the **service api** is often separated into a distinct bundle from both the **implementation** classes (i.e. the service 'host' that actually implements and exports the service) and the **service consumer** that discovers, imports, and uses the service (i.e. calls methods).

## Generating the Protobuf/Grpc Service API

For Protobuf + grpc-java, typically a service is defined by a .proto file with a service entry.  For example, [here](https://github.com/ECF/grpc-RemoteServicesProvider/blob/master/examples/org.eclipse.ecf.examples.provider.grpc.health.api/src/main/proto/health.proto) is a proto file for a simple 'HealthCheck' service

```proto
// Copyright 2015 The gRPC Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// The canonical version of this proto can be found at
// https://github.com/grpc/grpc-proto/blob/master/grpc/health/v1/health.proto

syntax = "proto3";

package grpc.health.v1;

option java_multiple_files = true;
option java_outer_classname = "HealthProto";
option java_package = "io.grpc.health.v1";

message HealthCheckRequest {
  string message = 1;
}

message HealthCheckResponse {
  enum ServingStatus {
    UNKNOWN = 0;
    SERVING = 1;
    NOT_SERVING = 2;
    SERVICE_UNKNOWN = 3;  // Used only by the Watch method.
  }
  ServingStatus status = 1;
}

service HealthCheck {
  // Unary method
  rpc Check(HealthCheckRequest) returns (HealthCheckResponse);
  // Server streaming method
  rpc WatchServer(HealthCheckRequest) returns (stream HealthCheckResponse);
  // Client streaming method
  rpc WatchClient(stream HealthCheckRequest) returns (HealthCheckResponse);
  // bidi streaming method
  rpc WatchBidi(stream HealthCheckRequest) returns (stream HealthCheckResponse);
}
```

## Service API Generation

Along with protoc and grpc-java compiler, and the [grpc-osgi-generator](https://github.com/ECF/grpc-osgi-generator) protobuf plugin it's now possible to generate the entire service api for a protobuf-declared service definition like the HealthCheck service shown above.

The directory [here](https://github.com/ECF/grpc-RemoteServicesProvider/tree/master/examples/org.eclipse.ecf.examples.provider.grpc.health.api/src/main/java/io/grpc/health/v1) has Java classes generated by using the protobuf-maven-plugin with protoc, grpc-java, and grpc-osgi-generator together.  

For an understanding of how to setup maven to support service code generation see either the [grpc-osgi-generator README.md](https://github.com/ECF/grpc-osgi-generator) and see the maven plugins defined for the healthcheck api example bundle [here](https://github.com/ECF/grpc-RemoteServicesProvider/blob/master/pom.xml).

## Service Impl and Consumer Creation

Running (via maven) the protobuf+grpc-java+grpc-osgi-generator creates the Java **service api**.  Once done, all that needs to complete the api bundle is to export the appropriate package in the bundle manifest.mf so that the implementation bundle and the consumer bundle can import the needed classes.  For reference, the completed example [health check service api bundle is here](https://github.com/ECF/grpc-RemoteServicesProvider/tree/master/examples/org.eclipse.ecf.examples.provider.grpc.health.api).  Note that the [HealthCheckService class](https://github.com/ECF/grpc-RemoteServicesProvider/blob/master/examples/org.eclipse.ecf.examples.provider.grpc.health.api/src/main/java/io/grpc/health/v1/HealthCheckService.java) is the service interface for this example and that this class was generated by the grpc-osgi-generator protobuf plugin.

The service implementation can now be created in a separate bundle that will run on the service host.  For reference, a [completed healthcheck implementation bundle is here](https://github.com/ECF/grpc-RemoteServicesProvider/tree/master/examples/org.eclipse.ecf.examples.provider.grpc.health.impl).  Note that the [HealthServiceImpl class](https://github.com/ECF/grpc-RemoteServicesProvider/blob/master/examples/org.eclipse.ecf.examples.provider.grpc.health.impl/src/org/eclipse/ecf/examples/provider/grpc/health/impl/HealthServiceImpl.java) implements the [HealthCheckService class](https://github.com/ECF/grpc-RemoteServicesProvider/blob/master/examples/org.eclipse.ecf.examples.provider.grpc.health.api/src/main/java/io/grpc/health/v1/HealthCheckService.java), and extends the [AbstractHealthCheckServiceImpl class](https://github.com/ECF/grpc-RemoteServicesProvider/blob/master/examples/org.eclipse.ecf.examples.provider.grpc.health.api/src/main/java/io/grpc/health/v1/AbstractHealthCheckServiceImpl.java), both of which were generated by the grpc-osgi-generator protobuf plugin.

Finally, the health check [service consumer is here](https://github.com/ECF/grpc-RemoteServicesProvider/blob/master/examples/org.eclipse.ecf.examples.provider.grpc.health.consumer/src/org/eclipse/ecf/examples/provider/grpc/health/consumer/HealthServiceConsumer.java) and it uses an injected instance of the HealthCheckService (the RSA-created proxy) to access the service/call it's **check** method.  For reference, the [completed healthcheck consumer bundle is here](https://github.com/ECF/grpc-RemoteServicesProvider/tree/master/examples/org.eclipse.ecf.examples.provider.grpc.health.consumer).

