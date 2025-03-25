package com.artemissoftware.hermesreceipts.core.presentation.util.extensions

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

fun Context.capturePhoto(
    controller: LifecycleCameraController,
    onPhotoCaptured: (Bitmap) -> Unit,
    onError: (String) -> Unit
){
    val mainExecutor: Executor = ContextCompat.getMainExecutor(this.applicationContext)

    controller.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            val bitmap: Bitmap = image
                .toBitmap()
                .rotateBitmap(image.imageInfo.rotationDegrees)

            onPhotoCaptured(bitmap)
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            onError(exception.message ?: "Error capturing image")
        }
    })
}
