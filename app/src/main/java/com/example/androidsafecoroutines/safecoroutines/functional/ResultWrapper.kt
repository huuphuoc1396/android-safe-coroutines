package com.example.androidsafecoroutines.safecoroutines.functional

import com.example.androidsafecoroutines.safecoroutines.failure.Failure
import com.example.androidsafecoroutines.safecoroutines.failure.FailureHandler
import com.example.androidsafecoroutines.safecoroutines.failure.UnknownFailure
import com.example.androidsafecoroutines.safecoroutines.functional.ResultWrapper.Error
import com.example.androidsafecoroutines.safecoroutines.functional.ResultWrapper.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

sealed class ResultWrapper<out R> {

    data class Error(val failure: Failure) : ResultWrapper<Nothing>()

    data class Success<out R>(val data: R) : ResultWrapper<R>()

    val isSuccess get() = this is Success<R>

    val isError get() = this is Error

    fun error(failure: Failure) = Error(failure)


    fun <R> success(b: R) = Success(b)

    fun fold(fnL: (Failure) -> Unit, fnR: (R) -> Unit): Unit = when (this) {
        is Error -> fnL(failure)
        is Success -> fnR(data)
    }
}

fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, R> ResultWrapper<R>.flatMap(fn: (R) -> ResultWrapper<T>): ResultWrapper<T> = when (this) {
    is Error -> Error(failure)
    is Success -> fn(data)
}

fun <T, R> ResultWrapper<R>.map(fn: (R) -> (T)): ResultWrapper<T> = this.flatMap(fn.c(::success))

fun <R> ResultWrapper<R>.getOrElse(value: R): R = when (this) {
    is Error -> value
    is Success -> data
}

fun <R> ResultWrapper<R>.getOrNull(): R? = when (this) {
    is Error -> null
    is Success -> data
}

fun <R> ResultWrapper<R>.onError(fn: (failure: Failure) -> Unit): ResultWrapper<R> =
    this.apply { if (this is Error) fn(failure) }

fun <R> ResultWrapper<R>.onSuccess(fn: (success: R) -> Unit): ResultWrapper<R> =
    this.apply { if (this is Success) fn(data) }

suspend fun <R> safeSuspend(
    vararg failureHandlers: FailureHandler,
    action: suspend () -> ResultWrapper<R>,
): ResultWrapper<R> = try {
    action()
} catch (exception: Exception) {
    failureHandlers.forEach { failureHandler ->
        val error = failureHandler.handleThrowable(exception)?.wrapError()
        if (error != null) {
            return error
        }
    }
    UnknownFailure(exception).wrapError()
}

suspend fun <R> safeSuspendIgnoreError(
    action: suspend () -> R
): R? = try {
    action()
} catch (exception: Exception) {
    null
}

fun <R> Flow<R>.asResult(
    failureHandlers: Array<out FailureHandler>,
): Flow<ResultWrapper<R>> = map { data ->
    Success(data)
}.catch { exception ->
    var handledException: Error? = null
    failureHandlers.forEach { failureHandler ->
        handledException = failureHandler.handleThrowable(exception)?.wrapError()
        if (handledException != null) return@forEach
    }
    handledException ?: UnknownFailure(exception)
}

fun <T> T.wrapSuccess() = Success(this)

fun Failure.wrapError() = Error(this)