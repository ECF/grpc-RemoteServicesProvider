// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: health.proto

package io.grpc.health.v1.rx3;

public interface HealthCheckResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.health.v1.HealthCheckResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.grpc.health.v1.HealthCheckResponse.ServingStatus status = 1;</code>
   * @return The enum numeric value on the wire for status.
   */
  int getStatusValue();
  /**
   * <code>.grpc.health.v1.HealthCheckResponse.ServingStatus status = 1;</code>
   * @return The status.
   */
  io.grpc.health.v1.rx3.HealthCheckResponse.ServingStatus getStatus();
}
