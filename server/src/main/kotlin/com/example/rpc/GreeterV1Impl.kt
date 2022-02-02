package com.example.rpc

import com.demo.proto.greeter.Greeter.GreetRequest
import com.demo.proto.greeter.Greeter.GreetResponse
import com.demo.proto.greeter.GreeterV1GrpcKt
import com.example.logic.GreeterLogic
import io.micronaut.grpc.annotation.GrpcService
import jakarta.inject.Inject


/** gRPC service which uses [GreeterLogic]. */
@GrpcService class GreeterV1Impl: GreeterV1GrpcKt.GreeterV1CoroutineImplBase() {
    @Inject lateinit var greeter: GreeterLogic

    /** @return Rendered greeting for the provided [request]. */
    override suspend fun greet(request: GreetRequest): GreetResponse {
        return GreetResponse.newBuilder()
            .setGreeting(greeter.renderGreeting(request.name, if (request.email?.isNotBlank() == true) {
                request.email
            } else {
                null
            }))
            .build()
    }
}
