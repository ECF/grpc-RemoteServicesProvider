package io.grpc.health.v1.rx3;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: health.proto")
public final class HealthCheckGrpc {

  private HealthCheckGrpc() {}

  public static final String SERVICE_NAME = "grpc.health.v1.HealthCheck";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest,
      io.grpc.health.v1.rx3.HealthCheckResponse> getCheckMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Check",
      requestType = io.grpc.health.v1.rx3.HealthCheckRequest.class,
      responseType = io.grpc.health.v1.rx3.HealthCheckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest,
      io.grpc.health.v1.rx3.HealthCheckResponse> getCheckMethod() {
    io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.health.v1.rx3.HealthCheckResponse> getCheckMethod;
    if ((getCheckMethod = HealthCheckGrpc.getCheckMethod) == null) {
      synchronized (HealthCheckGrpc.class) {
        if ((getCheckMethod = HealthCheckGrpc.getCheckMethod) == null) {
          HealthCheckGrpc.getCheckMethod = getCheckMethod =
              io.grpc.MethodDescriptor.<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.health.v1.rx3.HealthCheckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Check"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.rx3.HealthCheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.rx3.HealthCheckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthCheckMethodDescriptorSupplier("Check"))
              .build();
        }
      }
    }
    return getCheckMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest,
      io.grpc.health.v1.rx3.HealthCheckResponse> getWatchServerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "WatchServer",
      requestType = io.grpc.health.v1.rx3.HealthCheckRequest.class,
      responseType = io.grpc.health.v1.rx3.HealthCheckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest,
      io.grpc.health.v1.rx3.HealthCheckResponse> getWatchServerMethod() {
    io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.health.v1.rx3.HealthCheckResponse> getWatchServerMethod;
    if ((getWatchServerMethod = HealthCheckGrpc.getWatchServerMethod) == null) {
      synchronized (HealthCheckGrpc.class) {
        if ((getWatchServerMethod = HealthCheckGrpc.getWatchServerMethod) == null) {
          HealthCheckGrpc.getWatchServerMethod = getWatchServerMethod =
              io.grpc.MethodDescriptor.<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.health.v1.rx3.HealthCheckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "WatchServer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.rx3.HealthCheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.rx3.HealthCheckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthCheckMethodDescriptorSupplier("WatchServer"))
              .build();
        }
      }
    }
    return getWatchServerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest,
      io.grpc.health.v1.rx3.HealthCheckResponse> getWatchClientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "WatchClient",
      requestType = io.grpc.health.v1.rx3.HealthCheckRequest.class,
      responseType = io.grpc.health.v1.rx3.HealthCheckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest,
      io.grpc.health.v1.rx3.HealthCheckResponse> getWatchClientMethod() {
    io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.health.v1.rx3.HealthCheckResponse> getWatchClientMethod;
    if ((getWatchClientMethod = HealthCheckGrpc.getWatchClientMethod) == null) {
      synchronized (HealthCheckGrpc.class) {
        if ((getWatchClientMethod = HealthCheckGrpc.getWatchClientMethod) == null) {
          HealthCheckGrpc.getWatchClientMethod = getWatchClientMethod =
              io.grpc.MethodDescriptor.<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.health.v1.rx3.HealthCheckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "WatchClient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.rx3.HealthCheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.rx3.HealthCheckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthCheckMethodDescriptorSupplier("WatchClient"))
              .build();
        }
      }
    }
    return getWatchClientMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest,
      io.grpc.health.v1.rx3.HealthCheckResponse> getWatchBidiMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "WatchBidi",
      requestType = io.grpc.health.v1.rx3.HealthCheckRequest.class,
      responseType = io.grpc.health.v1.rx3.HealthCheckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest,
      io.grpc.health.v1.rx3.HealthCheckResponse> getWatchBidiMethod() {
    io.grpc.MethodDescriptor<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.health.v1.rx3.HealthCheckResponse> getWatchBidiMethod;
    if ((getWatchBidiMethod = HealthCheckGrpc.getWatchBidiMethod) == null) {
      synchronized (HealthCheckGrpc.class) {
        if ((getWatchBidiMethod = HealthCheckGrpc.getWatchBidiMethod) == null) {
          HealthCheckGrpc.getWatchBidiMethod = getWatchBidiMethod =
              io.grpc.MethodDescriptor.<io.grpc.health.v1.rx3.HealthCheckRequest, io.grpc.health.v1.rx3.HealthCheckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "WatchBidi"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.rx3.HealthCheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.rx3.HealthCheckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthCheckMethodDescriptorSupplier("WatchBidi"))
              .build();
        }
      }
    }
    return getWatchBidiMethod;
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
     * Unary method
     * </pre>
     */
    public void check(io.grpc.health.v1.rx3.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server streaming method
     * </pre>
     */
    public void watchServer(io.grpc.health.v1.rx3.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWatchServerMethod(), responseObserver);
    }

    /**
     * <pre>
     * Client streaming method
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckRequest> watchClient(
        io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getWatchClientMethod(), responseObserver);
    }

    /**
     * <pre>
     * bidi streaming method
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckRequest> watchBidi(
        io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getWatchBidiMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCheckMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.health.v1.rx3.HealthCheckRequest,
                io.grpc.health.v1.rx3.HealthCheckResponse>(
                  this, METHODID_CHECK)))
          .addMethod(
            getWatchServerMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                io.grpc.health.v1.rx3.HealthCheckRequest,
                io.grpc.health.v1.rx3.HealthCheckResponse>(
                  this, METHODID_WATCH_SERVER)))
          .addMethod(
            getWatchClientMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                io.grpc.health.v1.rx3.HealthCheckRequest,
                io.grpc.health.v1.rx3.HealthCheckResponse>(
                  this, METHODID_WATCH_CLIENT)))
          .addMethod(
            getWatchBidiMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                io.grpc.health.v1.rx3.HealthCheckRequest,
                io.grpc.health.v1.rx3.HealthCheckResponse>(
                  this, METHODID_WATCH_BIDI)))
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
     * Unary method
     * </pre>
     */
    public void check(io.grpc.health.v1.rx3.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server streaming method
     * </pre>
     */
    public void watchServer(io.grpc.health.v1.rx3.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getWatchServerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Client streaming method
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckRequest> watchClient(
        io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getWatchClientMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * bidi streaming method
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckRequest> watchBidi(
        io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getWatchBidiMethod(), getCallOptions()), responseObserver);
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
     * Unary method
     * </pre>
     */
    public io.grpc.health.v1.rx3.HealthCheckResponse check(io.grpc.health.v1.rx3.HealthCheckRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server streaming method
     * </pre>
     */
    public java.util.Iterator<io.grpc.health.v1.rx3.HealthCheckResponse> watchServer(
        io.grpc.health.v1.rx3.HealthCheckRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getWatchServerMethod(), getCallOptions(), request);
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
     * Unary method
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.health.v1.rx3.HealthCheckResponse> check(
        io.grpc.health.v1.rx3.HealthCheckRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK = 0;
  private static final int METHODID_WATCH_SERVER = 1;
  private static final int METHODID_WATCH_CLIENT = 2;
  private static final int METHODID_WATCH_BIDI = 3;

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
          serviceImpl.check((io.grpc.health.v1.rx3.HealthCheckRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>) responseObserver);
          break;
        case METHODID_WATCH_SERVER:
          serviceImpl.watchServer((io.grpc.health.v1.rx3.HealthCheckRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>) responseObserver);
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
        case METHODID_WATCH_CLIENT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.watchClient(
              (io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>) responseObserver);
        case METHODID_WATCH_BIDI:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.watchBidi(
              (io.grpc.stub.StreamObserver<io.grpc.health.v1.rx3.HealthCheckResponse>) responseObserver);
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
      return io.grpc.health.v1.rx3.HealthProto.getDescriptor();
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
              .addMethod(getWatchServerMethod())
              .addMethod(getWatchClientMethod())
              .addMethod(getWatchBidiMethod())
              .build();
        }
      }
    }
    return result;
  }
}
