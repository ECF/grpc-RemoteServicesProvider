package io.grpc.health.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.29.0)",
    comments = "Source: health.proto")
public final class HealthCheckGrpc {

  private HealthCheckGrpc() {}

  public static final String SERVICE_NAME = "grpc.health.v1.HealthCheck";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest,
      io.grpc.health.v1.HealthCheckResponse> getCheckMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "check",
      requestType = io.grpc.health.v1.HealthCheckRequest.class,
      responseType = io.grpc.health.v1.HealthCheckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest,
      io.grpc.health.v1.HealthCheckResponse> getCheckMethod() {
    io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest, io.grpc.health.v1.HealthCheckResponse> getCheckMethod;
    if ((getCheckMethod = HealthCheckGrpc.getCheckMethod) == null) {
      synchronized (HealthCheckGrpc.class) {
        if ((getCheckMethod = HealthCheckGrpc.getCheckMethod) == null) {
          HealthCheckGrpc.getCheckMethod = getCheckMethod =
              io.grpc.MethodDescriptor.<io.grpc.health.v1.HealthCheckRequest, io.grpc.health.v1.HealthCheckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "check"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.HealthCheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.HealthCheckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthCheckMethodDescriptorSupplier("check"))
              .build();
        }
      }
    }
    return getCheckMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest,
      io.grpc.health.v1.HealthCheckResponse> getWatchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Watch",
      requestType = io.grpc.health.v1.HealthCheckRequest.class,
      responseType = io.grpc.health.v1.HealthCheckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest,
      io.grpc.health.v1.HealthCheckResponse> getWatchMethod() {
    io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest, io.grpc.health.v1.HealthCheckResponse> getWatchMethod;
    if ((getWatchMethod = HealthCheckGrpc.getWatchMethod) == null) {
      synchronized (HealthCheckGrpc.class) {
        if ((getWatchMethod = HealthCheckGrpc.getWatchMethod) == null) {
          HealthCheckGrpc.getWatchMethod = getWatchMethod =
              io.grpc.MethodDescriptor.<io.grpc.health.v1.HealthCheckRequest, io.grpc.health.v1.HealthCheckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Watch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.HealthCheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.HealthCheckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthCheckMethodDescriptorSupplier("Watch"))
              .build();
        }
      }
    }
    return getWatchMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HealthCheckStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthCheckStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthCheckStub>() {
        @java.lang.Override
        public HealthCheckStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthCheckStub(channel, callOptions);
        }
      };
    return HealthCheckStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HealthCheckBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthCheckBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthCheckBlockingStub>() {
        @java.lang.Override
        public HealthCheckBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthCheckBlockingStub(channel, callOptions);
        }
      };
    return HealthCheckBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HealthCheckFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthCheckFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthCheckFutureStub>() {
        @java.lang.Override
        public HealthCheckFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthCheckFutureStub(channel, callOptions);
        }
      };
    return HealthCheckFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class HealthCheckImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * If the requested service is unknown, the call will fail with status
     * NOT_FOUND.
     * </pre>
     */
    public void check(io.grpc.health.v1.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCheckMethod(), responseObserver);
    }

    /**
     * <pre>
     * Performs a watch for the serving status of the requested service.
     * The server will immediately send back a message indicating the current
     * serving status.  It will then subsequently send a new message whenever
     * the service's serving status changes.
     * If the requested service is unknown when the call is received, the
     * server will send a message setting the serving status to
     * SERVICE_UNKNOWN but will *not* terminate the call.  If at some
     * future point, the serving status of the service becomes known, the
     * server will send a new message with the service's serving status.
     * If the call terminates with status UNIMPLEMENTED, then clients
     * should assume this method is not supported and should not retry the
     * call.  If the call terminates with any other status (including OK),
     * clients should retry the call with appropriate exponential backoff.
     * </pre>
     */
    public void watch(io.grpc.health.v1.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getWatchMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCheckMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.health.v1.HealthCheckRequest,
                io.grpc.health.v1.HealthCheckResponse>(
                  this, METHODID_CHECK)))
          .addMethod(
            getWatchMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                io.grpc.health.v1.HealthCheckRequest,
                io.grpc.health.v1.HealthCheckResponse>(
                  this, METHODID_WATCH)))
          .build();
    }
  }

  /**
   */
  public static final class HealthCheckStub extends io.grpc.stub.AbstractAsyncStub<HealthCheckStub> {
    private HealthCheckStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthCheckStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthCheckStub(channel, callOptions);
    }

    /**
     * <pre>
     * If the requested service is unknown, the call will fail with status
     * NOT_FOUND.
     * </pre>
     */
    public void check(io.grpc.health.v1.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCheckMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Performs a watch for the serving status of the requested service.
     * The server will immediately send back a message indicating the current
     * serving status.  It will then subsequently send a new message whenever
     * the service's serving status changes.
     * If the requested service is unknown when the call is received, the
     * server will send a message setting the serving status to
     * SERVICE_UNKNOWN but will *not* terminate the call.  If at some
     * future point, the serving status of the service becomes known, the
     * server will send a new message with the service's serving status.
     * If the call terminates with status UNIMPLEMENTED, then clients
     * should assume this method is not supported and should not retry the
     * call.  If the call terminates with any other status (including OK),
     * clients should retry the call with appropriate exponential backoff.
     * </pre>
     */
    public void watch(io.grpc.health.v1.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getWatchMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class HealthCheckBlockingStub extends io.grpc.stub.AbstractBlockingStub<HealthCheckBlockingStub> {
    private HealthCheckBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthCheckBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthCheckBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * If the requested service is unknown, the call will fail with status
     * NOT_FOUND.
     * </pre>
     */
    public io.grpc.health.v1.HealthCheckResponse check(io.grpc.health.v1.HealthCheckRequest request) {
      return blockingUnaryCall(
          getChannel(), getCheckMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Performs a watch for the serving status of the requested service.
     * The server will immediately send back a message indicating the current
     * serving status.  It will then subsequently send a new message whenever
     * the service's serving status changes.
     * If the requested service is unknown when the call is received, the
     * server will send a message setting the serving status to
     * SERVICE_UNKNOWN but will *not* terminate the call.  If at some
     * future point, the serving status of the service becomes known, the
     * server will send a new message with the service's serving status.
     * If the call terminates with status UNIMPLEMENTED, then clients
     * should assume this method is not supported and should not retry the
     * call.  If the call terminates with any other status (including OK),
     * clients should retry the call with appropriate exponential backoff.
     * </pre>
     */
    public java.util.Iterator<io.grpc.health.v1.HealthCheckResponse> watch(
        io.grpc.health.v1.HealthCheckRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getWatchMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class HealthCheckFutureStub extends io.grpc.stub.AbstractFutureStub<HealthCheckFutureStub> {
    private HealthCheckFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthCheckFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthCheckFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * If the requested service is unknown, the call will fail with status
     * NOT_FOUND.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.health.v1.HealthCheckResponse> check(
        io.grpc.health.v1.HealthCheckRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCheckMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK = 0;
  private static final int METHODID_WATCH = 1;

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
          serviceImpl.check((io.grpc.health.v1.HealthCheckRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse>) responseObserver);
          break;
        case METHODID_WATCH:
          serviceImpl.watch((io.grpc.health.v1.HealthCheckRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class HealthCheckBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HealthCheckBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.grpc.health.v1.HealthProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HealthCheck");
    }
  }

  private static final class HealthCheckFileDescriptorSupplier
      extends HealthCheckBaseDescriptorSupplier {
    HealthCheckFileDescriptorSupplier() {}
  }

  private static final class HealthCheckMethodDescriptorSupplier
      extends HealthCheckBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HealthCheckMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HealthCheckGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HealthCheckFileDescriptorSupplier())
              .addMethod(getCheckMethod())
              .addMethod(getWatchMethod())
              .build();
        }
      }
    }
    return result;
  }
}
