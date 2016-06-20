package io.grpc.examples.helloworld;

import java.util.concurrent.CompletableFuture;

public interface GreeterServiceAsync {

	CompletableFuture<HelloReply> sayHelloAsync(HelloRequest request);
}
