package com.muratkorkmazoglu.movie_app.core.data.remote

import retrofit2.Response


inline fun <reified T : Any> Response<T>.getBodyOrThrowError(): T {
    if (isSuccessful) {
        return body() ?: throw IllegalStateException("Response body is null.")
    } else
        return throw IllegalStateException("Response body is null.")
}

