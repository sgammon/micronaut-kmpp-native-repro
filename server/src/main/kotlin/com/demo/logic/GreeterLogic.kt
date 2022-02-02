
package com.demo.logic


/** Defines the API surface of sample Greeter logic. */
interface GreeterLogic {
    /**
     * Render a greeting with the provided [name] and optional [email].
     *
     * @param name User's name to greet.
     * @param email Optional email address.
     * @return Greeting text.
     */
    fun renderGreeting(name: String, email: String? = null): String
}
