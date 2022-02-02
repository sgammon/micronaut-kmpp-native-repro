package com.example.logic

import com.example.anno.Logic


/** V1 implementation of Greeter logic. */
@Logic class GreeterLogicV1: GreeterLogic {
    override fun renderGreeting(name: String, email: String?): String {
        return if (email == null) {
            "Hello, $name!"
        } else {
            "Hello, $name! ($email)"
        }
    }
}
