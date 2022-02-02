package com.example.anno

import io.micronaut.core.annotation.Introspected
import jakarta.inject.Singleton


/** Marks a class as introspect-able model. */
@Singleton
@MustBeDocumented
@Introspected
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Model
