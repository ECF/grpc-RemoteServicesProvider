# Example Go Client for the HealthCheck Remote Service

## Prerequisites
For system prerequisites check https://grpc.io/docs/languages/go/quickstart/#prerequisites. This example was written with `go1.16.3`.

## How to Run the Example

* Make sure the HealthCheck remote service example is running (see [Installing and Running the Example on Apache Karaf](https://github.com/ECF/grpc-RemoteServicesProvider/blob/master/README.md#installing-and-running-the-example-on-apache-karaf))

* Open a terminal and navigate to `examples/health.consumer.goapp`

* Run the main via `go run main.go`

* Console output should be something like

  ```
  HealthCheck service at hostname=localhost port=50002
  Calling check method...returned status=SERVING
  ```
  


## How the Go Code for the HealthCheck Example API Was Generated

1. The [health.proto](https://raw.githubusercontent.com/ECF/grpc-RemoteServicesProvider/master/examples/org.eclipse.ecf.examples.provider.grpc.health.api/src/main/proto/health.proto) file was copied to the `health.consumer.goapp/proto` folder
2. The `go_package` option was added to the `health.proto` file in line 25: `option go_package = "google.golang.org/grpc/examples/helloworld/helloworld";`
   * (Alternatively, the `M` flag for `protoc` could have been specified)
3. The code was generated by calling the following command in the `health.consumer.goapp/` directory:

```
protoc --go_out=. --go_opt=paths=source_relative \
--go-grpc_out=. --go-grpc_opt=paths=source_relative \
./proto/health.proto
```

:bulb: See https://grpc.io/docs/languages/go/basics/#generating-client-and-server-code
