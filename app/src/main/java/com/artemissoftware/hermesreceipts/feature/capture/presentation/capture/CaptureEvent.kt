package com.artemissoftware.hermesreceipts.feature.capture.presentation.capture

import android.graphics.Bitmap

sealed interface CaptureEvent {
    data class UpdateCapturedPhoto(val bitmap: Bitmap): CaptureEvent
}