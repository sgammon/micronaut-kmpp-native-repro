package com.example.svc

import com.demo.model.GreetRequest
import com.demo.model.GreetResponse
import com.example.logic.GreeterLogic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import jakarta.inject.Inject
import java.util.*


/** Pure Kotlin API services. */
@Controller("/api/greet") class GreeterAPI {
    @Inject lateinit var greeter: GreeterLogic

    /** Kotlin API method via GET. */
    @Get fun get(@QueryValue("name") name: Optional<String>): HttpResponse<GreetResponse> {
        return HttpResponse.ok(
            GreetResponse(
            greeting = greeter.renderGreeting(
                name = name.orElse("World")
            )
        )
        )
    }

    /** Kotlin API method via POST. */
    @Post fun post(@Body request: GreetRequest): HttpResponse<GreetResponse> {
        return HttpResponse.ok(
            GreetResponse(
            greeting = greeter.renderGreeting(
                name = request.name,
                email = request.email
            )
        )
        )
    }
}
