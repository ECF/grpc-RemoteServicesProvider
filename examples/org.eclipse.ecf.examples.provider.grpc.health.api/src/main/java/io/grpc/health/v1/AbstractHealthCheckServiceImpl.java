package io.grpc.health.v1;

import io.grpc.stub.StreamObserver;

@javax.annotation.Generated(
value = "by OSGi Remote Services generator",
comments = "Source: health.proto")
public abstract class AbstractHealthCheckServiceImpl extends HealthCheckGrpc.HealthCheckImplBase implements HealthCheckService {

    @Override
    public abstract io.grpc.health.v1.HealthCheckResponse check(io.grpc.health.v1.HealthCheckRequest request);

	/** 
	 * Override of HealthCheckGrpc.HealthCheckImplBase./2
	 */
    @Override
    public void check(io.grpc.health.v1.HealthCheckRequest request, StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
        responseObserver.onNext(check(request));
        responseObserver.onCompleted();
    }
    
}
