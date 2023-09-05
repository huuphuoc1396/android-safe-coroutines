package com.example.androidsafecoroutines.safecoroutines.failure

data class UnknownFailure(
    val throwable: Throwable,
) : Failure