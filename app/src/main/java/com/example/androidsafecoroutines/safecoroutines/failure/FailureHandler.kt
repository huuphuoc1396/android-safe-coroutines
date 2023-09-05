package com.example.androidsafecoroutines.safecoroutines.failure

interface FailureHandler {
    fun handleThrowable(throwable: Throwable): Failure?
}