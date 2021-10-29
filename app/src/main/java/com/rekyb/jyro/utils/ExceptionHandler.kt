package com.rekyb.jyro.utils

import android.content.Context
import com.rekyb.jyro.R
import com.rekyb.jyro.common.DataState
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ExceptionHandler @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val stringFromContext = { id: Int -> context.getString(id) }

    fun <T : Any> handleError(exception: Exception): DataState<T> {
        return when (exception) {
            is HttpException -> DataState.Error(getHttpExceptionCode(exception.code()))
            is SocketTimeoutException -> DataState.Error(stringFromContext(R.string.error_socket_timeout))
            is UnknownHostException -> DataState.Error(stringFromContext(R.string.error_unknown_host))
            else -> DataState.Error(stringFromContext(R.string.error_default))
        }
    }

    private fun getHttpExceptionCode(code: Int): String {
        return when (code) {
            400 -> stringFromContext(R.string.error_bad_request)
            401 -> stringFromContext(R.string.error_unauthorized)
            403 -> stringFromContext(R.string.error_forbidden)
            404 -> stringFromContext(R.string.error_not_found)
            500 -> stringFromContext(R.string.error_internal_server)
            else -> stringFromContext(R.string.error_not_identified)
        }
    }
}
