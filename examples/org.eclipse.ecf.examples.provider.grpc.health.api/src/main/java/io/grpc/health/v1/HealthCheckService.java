package io.grpc.health.v1;

import io.reactivex.Flowable;
import io.reactivex.Single;

@javax.annotation.Generated(
value = "by grpc-osgi-generator - A protoc plugin for ECF's grpc remote services distribution provider at https://github.com/ECF/grpc-RemoteServiceSProvider ",
comments = "Source: health.proto.  ")
public interface HealthCheckService {

    
    default Single<io.grpc.health.v1.HealthCheckResponse> check(Single<io.grpc.health.v1.HealthCheckRequest> requests)  {
        return null;
    }
    
    /**
     * <pre>
     *  Server streaming method
     * </pre>
     */
    default Flowable<io.grpc.health.v1.HealthCheckResponse> watchServer(Single<io.grpc.health.v1.HealthCheckRequest> requests)  {
        return null;
    }
    
    /**
     * <pre>
     *  Client streaming method
     * </pre>
     */
    default Single<io.grpc.health.v1.HealthCheckResponse> watchClient(Flowable<io.grpc.health.v1.HealthCheckRequest> requests)  {
        return null;
    }
    
    /**
     * <pre>
     *  bidi streaming method
     * </pre>
     */
    default Flowable<io.grpc.health.v1.HealthCheckResponse> watchBidi(Flowable<io.grpc.health.v1.HealthCheckRequest> requests)  {
        return null;
    }
}
