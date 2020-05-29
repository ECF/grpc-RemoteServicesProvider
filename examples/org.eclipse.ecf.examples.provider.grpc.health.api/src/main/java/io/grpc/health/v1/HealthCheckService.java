package io.grpc.health.v1;


@javax.annotation.Generated(
value = "by OSGi Remote Services generator",
comments = "Source: health.proto")
public interface HealthCheckService {

    
    default io.grpc.health.v1.HealthCheckResponse check(io.grpc.health.v1.HealthCheckRequest request) {
        return null;
    }
    
    /**
     * <pre>
     *  Server streaming method
     * </pre>
     */
    default io.reactivex.Flowable<io.grpc.health.v1.HealthCheckResponse> watchServer(io.reactivex.Single<io.grpc.health.v1.HealthCheckRequest> request)  {
        return null;
    }
    
    /**
     * <pre>
     *  Client streaming method
     * </pre>
     */
    default io.reactivex.Single<io.grpc.health.v1.HealthCheckResponse> watchClient(io.reactivex.Flowable<io.grpc.health.v1.HealthCheckRequest> requests)  {
        return null;
    }
    
    /**
     * <pre>
     *  bidi streaming method
     * </pre>
     */
    default io.reactivex.Flowable<io.grpc.health.v1.HealthCheckResponse> watchBidi(io.reactivex.Flowable<io.grpc.health.v1.HealthCheckRequest> requests)  {
        return null;
    }
}
