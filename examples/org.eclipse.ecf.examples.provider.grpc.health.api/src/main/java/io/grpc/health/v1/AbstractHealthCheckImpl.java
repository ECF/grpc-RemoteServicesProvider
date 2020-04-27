package io.grpc.health.v1;

import io.grpc.stub.StreamObserver;

public abstract class AbstractHealthCheckImpl extends HealthCheckGrpc.HealthCheckImplBase implements HealthCheckIntf {

    @Override
    public abstract io.grpc.health.v1.HealthCheckResponse check(io.grpc.health.v1.HealthCheckRequest request);

    public void check(io.grpc.health.v1.HealthCheckRequest request, StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
        responseObserver.onNext(check(request));
        responseObserver.onCompleted();
    }
}
