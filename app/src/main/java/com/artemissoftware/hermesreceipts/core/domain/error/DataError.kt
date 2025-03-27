package com.artemissoftware.hermesreceipts.core.domain.error

sealed interface DataError : Error {

    sealed class ImageError : DataError {
        data class Error(val message: String? = "Error processing image") : ImageError()
        data object CreateImage : ImageError()
        data object Uri : ImageError()
    }

    sealed class NetworkError : DataError {
        data class Error(val message: String) : NetworkError()
        data object SocketTimeout : NetworkError()
        data object Unknown : NetworkError()
        data object UnknownHost : NetworkError()
        data object Connect : NetworkError()
    }
}