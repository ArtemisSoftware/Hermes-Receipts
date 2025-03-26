package com.artemissoftware.hermesreceipts.feature.capture.presentation

import android.graphics.Bitmap

data class CaptureState(
    val isLoading: Boolean = false,
    val capturedBitmap: Bitmap? = null,
    val imagePath: String? = null,
)
