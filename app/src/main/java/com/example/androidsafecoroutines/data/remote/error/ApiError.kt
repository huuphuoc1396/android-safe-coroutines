package com.example.androidsafecoroutines.data.remote.error

import com.example.androidsafecoroutines.safecoroutines.failure.Failure

sealed class ApiError : Failure {

    data object Connection : ApiError()

    data class Server(
        val code: Int,
        val errorMessage: String,
    ) : ApiError()

    data object Unauthorized : ApiError()
}