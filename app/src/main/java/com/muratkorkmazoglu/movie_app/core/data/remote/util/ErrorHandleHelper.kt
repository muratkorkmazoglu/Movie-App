package com.muratkorkmazoglu.movie_app.core.data.remote.util


/*
inline fun <reified T : Any> throwErrorWithTokenResponse(response: Response<*>): Nothing {
    val responseModel = Gson().fromJson(response.errorBody()?.string(), T::class.java)
    val baseResponse = (responseModel as? BaseResponse)
    baseResponse?.friendlyMessage?.let {
        throw FriendlyMessageException(it)
    } ?: run {
        throw Throwable()
    }
}

inline fun <reified T : Any> Response<T>.getBodyOrThrowError(): T {
    if (isSuccessful) {
        return body() ?: throw IllegalStateException("Response body is null.")
    } else {
        throwErrorWithTokenResponse<T>(this)
    }
}

fun Throwable?.getErrorMessage(): ExceptionModel {
    return when (this) {
        is FriendlyMessageException -> ExceptionModel(
            icon = R.drawable.ic_error,
            title = this.friendlyMessage?.title ?: kotlin.run { "Bir Sorun Oluştu" },
            description = this.friendlyMessage?.message
                ?: kotlin.run { "Daha sonra tekrar deneyin" },
            buttonText = this.friendlyMessage?.buttonPositive.orEmpty()
        )
        is NetworkException -> ExceptionModel(
            icon = R.drawable.ic_connection_error,
            title = "Connection Problem!",
            description = "Vivamus quis gravida velit. Sed non interdum magna, a commodo orci.",
            buttonText = "Retry"
        )
        else -> ExceptionModel(
            icon = R.drawable.ic_error,
            title = "Bir Sorun Oluştu",
            description = "Daha sonra tekrar deneyin",
        )
    }
}*/
