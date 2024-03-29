package io.grpc.health.v1.rx3;

import static io.grpc.health.v1.rx3.HealthCheckGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;


@javax.annotation.Generated(
value = "by RxGrpc generator",
comments = "Source: health.proto")
public final class Rx3HealthCheckGrpc {
    private Rx3HealthCheckGrpc() {}

    public static RxHealthCheckStub newRxStub(io.grpc.Channel channel) {
        return new RxHealthCheckStub(channel);
    }

    public static final class RxHealthCheckStub extends io.grpc.stub.AbstractStub<RxHealthCheckStub> {
        private HealthCheckGrpc.HealthCheckStub delegateStub;

        private RxHealthCheckStub(io.grpc.Channel channel) {
            super(channel);
            delegateStub = HealthCheckGrpc.newStub(channel);
        }

        private RxHealthCheckStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
            delegateStub = HealthCheckGrpc.newStub(channel).build(channel, callOptions);
        }

        @java.lang.Override
        protected RxHealthCheckStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new RxHealthCheckStub(channel, callOptions);
        }

        public io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckResponse> check(io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckRequest> rxRequest) {
            return com.salesforce.rx3grpc.stub.ClientCalls.oneToOne(rxRequest,
                new com.salesforce.reactivegrpc.common.BiConsumer<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>>() {
                    @java.lang.Override
                    public void accept(io.grpc.health.v1.rx3.HealthCheckRequest request, io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> observer) {
                        delegateStub.check(request, observer);
                    }
                }, getCallOptions());
        }

        /**
         * <pre>
         *  Server streaming method
         * </pre>
         */
        public io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckResponse> watchServer(io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckRequest> rxRequest) {
            return com.salesforce.rx3grpc.stub.ClientCalls.oneToMany(rxRequest,
                new com.salesforce.reactivegrpc.common.BiConsumer<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>>() {
                    @java.lang.Override
                    public void accept(io.grpc.health.v1.rx3.HealthCheckRequest request, io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> observer) {
                        delegateStub.watchServer(request, observer);
                    }
                }, getCallOptions());
        }

        /**
         * <pre>
         *  Client streaming method
         * </pre>
         */
        public io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckResponse> watchClient(io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckRequest> rxRequest) {
            return com.salesforce.rx3grpc.stub.ClientCalls.manyToOne(rxRequest,
                new com.salesforce.reactivegrpc.common.Function<io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>, io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckRequest>>() {
                    @java.lang.Override
                    public io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckRequest> apply(io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> observer) {
                        return delegateStub.watchClient(observer);
                    }
                }, getCallOptions());
        }

        /**
         * <pre>
         *  bidi streaming method
         * </pre>
         */
        public io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckResponse> watchBidi(io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckRequest> rxRequest) {
            return com.salesforce.rx3grpc.stub.ClientCalls.manyToMany(rxRequest,
                new com.salesforce.reactivegrpc.common.Function<io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>, io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckRequest>>() {
                    @java.lang.Override
                    public io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckRequest> apply(io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> observer) {
                        return delegateStub.watchBidi(observer);
                    }
                }, getCallOptions());
        }

        public io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckResponse> check(io.grpc.health.v1.rx3.HealthCheckRequest rxRequest) {
            return com.salesforce.rx3grpc.stub.ClientCalls.oneToOne(io.reactivex.rxjava3.core.Single.just(rxRequest),
                new com.salesforce.reactivegrpc.common.BiConsumer<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>>() {
                    @java.lang.Override
                    public void accept(io.grpc.health.v1.rx3.HealthCheckRequest request, io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> observer) {
                        delegateStub.check(request, observer);
                    }
                }, getCallOptions());
        }

        /**
         * <pre>
         *  Server streaming method
         * </pre>
         */
        public io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckResponse> watchServer(io.grpc.health.v1.rx3.HealthCheckRequest rxRequest) {
            return com.salesforce.rx3grpc.stub.ClientCalls.oneToMany(io.reactivex.rxjava3.core.Single.just(rxRequest),
                new com.salesforce.reactivegrpc.common.BiConsumer<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>>() {
                    @java.lang.Override
                    public void accept(io.grpc.health.v1.rx3.HealthCheckRequest request, io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> observer) {
                        delegateStub.watchServer(request, observer);
                    }
                }, getCallOptions());
        }

    }

    public static abstract class HealthCheckImplBase implements io.grpc.BindableService {

        public io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckResponse> check(io.grpc.health.v1.rx3.HealthCheckRequest request) {
            return check(io.reactivex.rxjava3.core.Single.just(request));
        }

        public io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckResponse> check(io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        /**
         * <pre>
         *  Server streaming method
         * </pre>
         */
        public io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckResponse> watchServer(io.grpc.health.v1.rx3.HealthCheckRequest request) {
            return watchServer(io.reactivex.rxjava3.core.Single.just(request));
        }

        /**
         * <pre>
         *  Server streaming method
         * </pre>
         */
        public io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckResponse> watchServer(io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }


        /**
         * <pre>
         *  Client streaming method
         * </pre>
         */
        public io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckResponse> watchClient(io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }


        /**
         * <pre>
         *  bidi streaming method
         * </pre>
         */
        public io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckResponse> watchBidi(io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            io.grpc.health.v1.rx3.HealthCheckGrpc.getCheckMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            io.grpc.health.v1.rx3.HealthCheckRequest,
                                            io.grpc.health.v1.rx3.HealthCheckResponse>(
                                            this, METHODID_CHECK)))
                    .addMethod(
                            io.grpc.health.v1.rx3.HealthCheckGrpc.getWatchServerMethod(),
                            asyncServerStreamingCall(
                                    new MethodHandlers<
                                            io.grpc.health.v1.rx3.HealthCheckRequest,
                                            io.grpc.health.v1.rx3.HealthCheckResponse>(
                                            this, METHODID_WATCH_SERVER)))
                    .addMethod(
                            io.grpc.health.v1.rx3.HealthCheckGrpc.getWatchClientMethod(),
                            asyncClientStreamingCall(
                                    new MethodHandlers<
                                            io.grpc.health.v1.rx3.HealthCheckRequest,
                                            io.grpc.health.v1.rx3.HealthCheckResponse>(
                                            this, METHODID_WATCH_CLIENT)))
                    .addMethod(
                            io.grpc.health.v1.rx3.HealthCheckGrpc.getWatchBidiMethod(),
                            asyncBidiStreamingCall(
                                    new MethodHandlers<
                                            io.grpc.health.v1.rx3.HealthCheckRequest,
                                            io.grpc.health.v1.rx3.HealthCheckResponse>(
                                            this, METHODID_WATCH_BIDI)))
                    .build();
        }

        protected io.grpc.CallOptions getCallOptions(int methodId) {
            return null;
        }

        protected Throwable onErrorMap(Throwable throwable) {
            return com.salesforce.rx3grpc.stub.ServerCalls.prepareError(throwable);
        }

    }

    public static final int METHODID_CHECK = 0;
    public static final int METHODID_WATCH_SERVER = 1;
    public static final int METHODID_WATCH_CLIENT = 2;
    public static final int METHODID_WATCH_BIDI = 3;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final HealthCheckImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(HealthCheckImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_CHECK:
                    com.salesforce.rx3grpc.stub.ServerCalls.oneToOne((io.grpc.health.v1.rx3.HealthCheckRequest) request,
                            (io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>) responseObserver,
                            new com.salesforce.reactivegrpc.common.Function<io.grpc.health.v1.rx3.HealthCheckRequest, io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckResponse>>() {
                                @java.lang.Override
                                public io.reactivex.rxjava3.core.Single<io.grpc.health.v1.rx3.HealthCheckResponse> apply(io.grpc.health.v1.rx3.HealthCheckRequest single) {
                                    return serviceImpl.check(single);
                                }
                            }, serviceImpl::onErrorMap);
                    break;
                case METHODID_WATCH_SERVER:
                    com.salesforce.rx3grpc.stub.ServerCalls.oneToMany((io.grpc.health.v1.rx3.HealthCheckRequest) request,
                            (io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>) responseObserver,
                            new com.salesforce.reactivegrpc.common.Function<io.grpc.health.v1.rx3.HealthCheckRequest, io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckResponse>>() {
                                @java.lang.Override
                                public io.reactivex.rxjava3.core.Flowable<io.grpc.health.v1.rx3.HealthCheckResponse> apply(io.grpc.health.v1.rx3.HealthCheckRequest single) {
                                    return serviceImpl.watchServer(single);
                                }
                            }, serviceImpl::onErrorMap);
                    break;
                default:
                    throw new java.lang.AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_WATCH_CLIENT:
                    return (io.grpc.stub.StreamObserver<Req>) com.salesforce.rx3grpc.stub.ServerCalls.manyToOne(
                            (io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>) responseObserver,
                            serviceImpl::watchClient, serviceImpl::onErrorMap, serviceImpl.getCallOptions(methodId));
                case METHODID_WATCH_BIDI:
                    return (io.grpc.stub.StreamObserver<Req>) com.salesforce.rx3grpc.stub.ServerCalls.manyToMany(
                            (io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>) responseObserver,
                            serviceImpl::watchBidi, serviceImpl::onErrorMap, serviceImpl.getCallOptions(methodId));
                default:
                    throw new java.lang.AssertionError();
            }
        }
    }

}
