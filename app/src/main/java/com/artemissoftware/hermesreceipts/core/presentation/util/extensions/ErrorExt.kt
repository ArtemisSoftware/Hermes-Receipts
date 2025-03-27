package com.artemissoftware.hermesreceipts.core.presentation.util.extensions

import com.artemissoftware.hermesreceipts.R
import com.artemissoftware.hermesreceipts.core.domain.error.DataError
import com.artemissoftware.hermesreceipts.core.presentation.util.UiText
import com.artemissoftware.hermesreceipts.core.domain.error.Error

fun Error.toUiText(): UiText {
    return when (this) {
        is DataError.NetworkError -> {
            this.asUiText()
        }
        is DataError.ImageError -> {
            this.asUiText()
        }
        else -> UiText.StringResource(R.string.error_not_mapped)
    }
}

private fun DataError.ImageError.asUiText(): UiText {
    return when (this) {
        DataError.ImageError.CreateImage -> UiText.StringResource(
            R.string.couldn_t_create_file_for_gallery,
        )

        is DataError.ImageError.Error -> UiText.DynamicString(this.message ?: "")
    }
}

private fun DataError.NetworkError.asUiText(): UiText {
    return when (this) {
        DataError.NetworkError.Connect -> UiText.StringResource(
            R.string.connection_error,
        )
        is DataError.NetworkError.Error -> UiText.DynamicString(this.message)
        DataError.NetworkError.SocketTimeout -> UiText.StringResource(
            R.string.connection_socket_time_out,
        )
        DataError.NetworkError.Unknown -> UiText.StringResource(
            R.string.unknown_error,
        )
        DataError.NetworkError.UnknownHost -> UiText.StringResource(
            R.string.unknown_host_error,
        )
    }
}