package com.demo.anno

import jakarta.inject.Singleton


/** Marks a class as server-side logic. */
@Singleton
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Logic
