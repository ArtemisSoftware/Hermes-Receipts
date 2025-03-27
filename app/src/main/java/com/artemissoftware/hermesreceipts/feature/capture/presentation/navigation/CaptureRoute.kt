package com.artemissoftware.hermesreceipts.feature.capture.presentation.navigation

import kotlinx.serialization.Serializable

sealed class CaptureRoute {
    @Serializable
    data object Capture

    companion object {
        const val VALIDATION = "validation"
        const val ERROR = "error"
    }
}