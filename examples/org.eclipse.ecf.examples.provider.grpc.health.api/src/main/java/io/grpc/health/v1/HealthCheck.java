package io.grpc.health.v1;

public interface HealthCheck {

    public HealthCheckResponse check(HealthCheckRequest request);

}
