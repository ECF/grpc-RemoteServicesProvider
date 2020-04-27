package io.grpc.health.v1;

public interface HealthCheckIntf {

    public io.grpc.health.v1.HealthCheckResponse check(io.grpc.health.v1.HealthCheckRequest request);

}
