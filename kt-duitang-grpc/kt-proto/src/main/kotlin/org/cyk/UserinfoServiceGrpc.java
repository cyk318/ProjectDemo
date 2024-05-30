package org.cyk;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.52.0)",
    comments = "Source: user.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UserinfoServiceGrpc {

  private UserinfoServiceGrpc() {}

  public static final String SERVICE_NAME = "UserinfoService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.cyk.UserinfoProto.LoginReq,
      org.cyk.UserinfoProto.LoginResp> getLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "login",
      requestType = org.cyk.UserinfoProto.LoginReq.class,
      responseType = org.cyk.UserinfoProto.LoginResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cyk.UserinfoProto.LoginReq,
      org.cyk.UserinfoProto.LoginResp> getLoginMethod() {
    io.grpc.MethodDescriptor<org.cyk.UserinfoProto.LoginReq, org.cyk.UserinfoProto.LoginResp> getLoginMethod;
    if ((getLoginMethod = UserinfoServiceGrpc.getLoginMethod) == null) {
      synchronized (UserinfoServiceGrpc.class) {
        if ((getLoginMethod = UserinfoServiceGrpc.getLoginMethod) == null) {
          UserinfoServiceGrpc.getLoginMethod = getLoginMethod =
              io.grpc.MethodDescriptor.<org.cyk.UserinfoProto.LoginReq, org.cyk.UserinfoProto.LoginResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "login"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cyk.UserinfoProto.LoginReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cyk.UserinfoProto.LoginResp.getDefaultInstance()))
              .setSchemaDescriptor(new UserinfoServiceMethodDescriptorSupplier("login"))
              .build();
        }
      }
    }
    return getLoginMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.cyk.UserinfoProto.RegReq,
      org.cyk.UserinfoProto.RegResp> getRegMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reg",
      requestType = org.cyk.UserinfoProto.RegReq.class,
      responseType = org.cyk.UserinfoProto.RegResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.cyk.UserinfoProto.RegReq,
      org.cyk.UserinfoProto.RegResp> getRegMethod() {
    io.grpc.MethodDescriptor<org.cyk.UserinfoProto.RegReq, org.cyk.UserinfoProto.RegResp> getRegMethod;
    if ((getRegMethod = UserinfoServiceGrpc.getRegMethod) == null) {
      synchronized (UserinfoServiceGrpc.class) {
        if ((getRegMethod = UserinfoServiceGrpc.getRegMethod) == null) {
          UserinfoServiceGrpc.getRegMethod = getRegMethod =
              io.grpc.MethodDescriptor.<org.cyk.UserinfoProto.RegReq, org.cyk.UserinfoProto.RegResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reg"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cyk.UserinfoProto.RegReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.cyk.UserinfoProto.RegResp.getDefaultInstance()))
              .setSchemaDescriptor(new UserinfoServiceMethodDescriptorSupplier("reg"))
              .build();
        }
      }
    }
    return getRegMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserinfoServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserinfoServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserinfoServiceStub>() {
        @java.lang.Override
        public UserinfoServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserinfoServiceStub(channel, callOptions);
        }
      };
    return UserinfoServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserinfoServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserinfoServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserinfoServiceBlockingStub>() {
        @java.lang.Override
        public UserinfoServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserinfoServiceBlockingStub(channel, callOptions);
        }
      };
    return UserinfoServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserinfoServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserinfoServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserinfoServiceFutureStub>() {
        @java.lang.Override
        public UserinfoServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserinfoServiceFutureStub(channel, callOptions);
        }
      };
    return UserinfoServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class UserinfoServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void login(org.cyk.UserinfoProto.LoginReq request,
        io.grpc.stub.StreamObserver<org.cyk.UserinfoProto.LoginResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginMethod(), responseObserver);
    }

    /**
     */
    public void reg(org.cyk.UserinfoProto.RegReq request,
        io.grpc.stub.StreamObserver<org.cyk.UserinfoProto.RegResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLoginMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.cyk.UserinfoProto.LoginReq,
                org.cyk.UserinfoProto.LoginResp>(
                  this, METHODID_LOGIN)))
          .addMethod(
            getRegMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.cyk.UserinfoProto.RegReq,
                org.cyk.UserinfoProto.RegResp>(
                  this, METHODID_REG)))
          .build();
    }
  }

  /**
   */
  public static final class UserinfoServiceStub extends io.grpc.stub.AbstractAsyncStub<UserinfoServiceStub> {
    private UserinfoServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserinfoServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserinfoServiceStub(channel, callOptions);
    }

    /**
     */
    public void login(org.cyk.UserinfoProto.LoginReq request,
        io.grpc.stub.StreamObserver<org.cyk.UserinfoProto.LoginResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reg(org.cyk.UserinfoProto.RegReq request,
        io.grpc.stub.StreamObserver<org.cyk.UserinfoProto.RegResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UserinfoServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<UserinfoServiceBlockingStub> {
    private UserinfoServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserinfoServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserinfoServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.cyk.UserinfoProto.LoginResp login(org.cyk.UserinfoProto.LoginReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.cyk.UserinfoProto.RegResp reg(org.cyk.UserinfoProto.RegReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UserinfoServiceFutureStub extends io.grpc.stub.AbstractFutureStub<UserinfoServiceFutureStub> {
    private UserinfoServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserinfoServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserinfoServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cyk.UserinfoProto.LoginResp> login(
        org.cyk.UserinfoProto.LoginReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.cyk.UserinfoProto.RegResp> reg(
        org.cyk.UserinfoProto.RegReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN = 0;
  private static final int METHODID_REG = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UserinfoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UserinfoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOGIN:
          serviceImpl.login((org.cyk.UserinfoProto.LoginReq) request,
              (io.grpc.stub.StreamObserver<org.cyk.UserinfoProto.LoginResp>) responseObserver);
          break;
        case METHODID_REG:
          serviceImpl.reg((org.cyk.UserinfoProto.RegReq) request,
              (io.grpc.stub.StreamObserver<org.cyk.UserinfoProto.RegResp>) responseObserver);
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

  private static abstract class UserinfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserinfoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.cyk.UserinfoProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserinfoService");
    }
  }

  private static final class UserinfoServiceFileDescriptorSupplier
      extends UserinfoServiceBaseDescriptorSupplier {
    UserinfoServiceFileDescriptorSupplier() {}
  }

  private static final class UserinfoServiceMethodDescriptorSupplier
      extends UserinfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UserinfoServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (UserinfoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserinfoServiceFileDescriptorSupplier())
              .addMethod(getLoginMethod())
              .addMethod(getRegMethod())
              .build();
        }
      }
    }
    return result;
  }
}
