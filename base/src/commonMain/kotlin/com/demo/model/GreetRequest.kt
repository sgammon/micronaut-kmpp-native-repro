package com.demo.model

import kotlinx.serialization.Serializable


/** Describes a request to render a greeting. */
@Serializable data class GreetRequest (
    var name: String,
    var email: String?
)
