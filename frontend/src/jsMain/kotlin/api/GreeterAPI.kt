package api

import com.demo.model.GreetRequest
import com.demo.model.GreetResponse
import kotlinx.browser.window
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlin.js.Promise
import org.w3c.fetch.*


/** Static API configuration. */
object APIConfig {
    /** API base URL. */
    val apiUrl = if (window.location.port != "") {
        "https://${window.location.hostname}:${window.location.port}/api"
    } else {
        "https://${window.location.hostname}/api"
    }

    /** Default request headers. */
    val requestHeaders = mapOf(
        "Accept" to "application/json",
        "Content-Type" to "application/json"
    )
}


/** Enumerates available RESTful API methods. */
enum class APIMethod constructor (private val endpoint: String) {
    GREET("greet");

    fun apiURL(): String {
        return "${APIConfig.apiUrl}/${this.endpoint}"
    }
}


/**
 * Generate a REST API call to a backend API endpoint hosted by this same service. In this case, the backend endpoint is
 * powered by pure Kotlin and related underlying libraries (Micronaut, KotlinX, etc).
 *
 * @param name Name to generate a greeting for.
 * @param email Optional email to enclose in the greeting.
 * @return Promise which resolves to a [GreetResponse] -- the rendered greeting.
 */
@JsExport
fun greet(name: String = "World", email: String? = null): Promise<String> = Promise { resolve, reject ->
    val headers = Headers()
    APIConfig.requestHeaders.entries.forEach {
        headers.append(it.key, it.value)
    }

    window.fetch(APIMethod.GREET.apiURL(), object : RequestInit {
        override var method: String? = "POST"
        override var body: dynamic = Json.encodeToString(GreetRequest(name, email))
        override var headers: dynamic = headers
    }).then({ response ->
        response.text().then { data ->
            resolve.invoke(
                (Json.decodeFromString(data) as GreetResponse).greeting
            )
        }
    }, reject)
}
