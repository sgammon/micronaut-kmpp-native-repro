@file:Suppress("UnusedImport")

import react.dom.render

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinext.js.*

import api.greet
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlin.js.Promise


fun greetAPI(): Promise<String> {
    console.log("Executing `greet` via REST call...")
    return greet("REST").then {
        console.log("Received from API: '$it'")
        it
    }
}


@OptIn(DelicateCoroutinesApi::class)
fun main() {
    window.onload = {
        GlobalScope.launch {
            greetAPI().then { renderedGreeting ->
                render(document.getElementById("root")!!) {
                    child(Welcome::class) {
                        attrs {
                            name = "Kotlin/JS"
                        }
                    }
                    child(APIMessage::class) {
                        attrs {
                            greeting = renderedGreeting
                        }
                    }
                }
            }
        }
    }
}
