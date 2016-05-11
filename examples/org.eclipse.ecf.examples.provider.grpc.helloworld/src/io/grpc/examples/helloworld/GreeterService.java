package io.grpc.examples.helloworld;

public interface GreeterService {

	HelloReply sayHello(HelloRequest request);
}
