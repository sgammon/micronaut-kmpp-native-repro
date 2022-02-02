package com.example

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.QueryValue
import io.micronaut.views.View
import java.util.*


@Controller("/")
class DemoController {
    @View("index")
    @Get(uri = "/", produces = [MediaType.TEXT_HTML])
    fun index(@QueryValue("name") name: Optional<String>): HttpResponse<Map<String, Any>> {
        return HttpResponse.ok(
            mapOf("name" to name.orElse("World"))
        )
    }
}
