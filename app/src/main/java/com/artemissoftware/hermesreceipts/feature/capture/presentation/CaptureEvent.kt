package com.artemissoftware.hermesreceipts.feature.capture.presentation

import android.graphics.Bitmap
import android.net.Uri

sealed interface CaptureEvent {
    data class UpdateCapturedPhoto(val bitmap: Bitmap): CaptureEvent
}