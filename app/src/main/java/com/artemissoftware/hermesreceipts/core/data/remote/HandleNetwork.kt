package com.artemissoftware.hermesreceipts.core.data.remote

import com.artemissoftware.hermesreceipts.core.domain.Resource
import com.artemissoftware.hermesreceipts.core.domain.error.DataError
import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto.ErrorDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

internal object HandleNetwork {

    inline fun <T> safeNetworkCall(apiCall: () -> T): Resource<T> {
        return try {
            Resource.Success(data = apiCall())
        } catch (ex: Exception) {
            Resource.Failure(error = handleException(ex))
        }
    }

    fun handleException(ex: Exception): DataError {
        return when (ex) {
            is HttpException -> {
                convertErrorBody(ex)?.let {
                    DataError.NetworkError.Error(message = it.message)
                } ?: run {
                    DataError.NetworkError.Unknown
                }
            }
            is UnknownHostException -> {
                DataError.NetworkError.UnknownHost
            }

            is ConnectException -> {
                DataError.NetworkError.Connect
            }

            is SocketTimeoutException -> {
                DataError.NetworkError.SocketTimeout
            }
            is CancellationException -> {
                throw ex
            }
            else -> DataError.NetworkError.Unknown
        }
    }

    private fun convertErrorBody(httpException: HttpException): ErrorDto? {
        return try {
            httpException.response()?.errorBody()?.let {

                val moshi: Moshi = Moshi.Builder().build()
                val adapter: JsonAdapter<ErrorDto> = moshi.adapter(ErrorDto::class.java)
                adapter.fromJson(it.string())
            }
        } catch (exception: Exception) {
            null
        }
    }
}