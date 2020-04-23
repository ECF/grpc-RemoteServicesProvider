package io.grpc.health.v1;

import io.grpc.stub.StreamObserver;

public abstract class AbstractHealthCheckImpl extends HealthCheckGrpc.HealthCheckImplBase implements HealthCheck {

    public abstract HealthCheckResponse check(HealthCheckRequest request);

    public void check(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        responseObserver.onNext(check(request));
        responseObserver.onCompleted();
    }
}
