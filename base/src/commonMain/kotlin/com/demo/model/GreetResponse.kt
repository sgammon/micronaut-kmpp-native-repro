package com.demo.model

import kotlinx.serialization.Serializable


/** Describes a rendered greeting. */
@Serializable data class GreetResponse (
    var greeting: String
)
