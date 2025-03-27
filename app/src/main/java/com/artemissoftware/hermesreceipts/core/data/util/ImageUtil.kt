package com.artemissoftware.hermesreceipts.core.data.util

import android.content.Context
import android.net.Uri
import com.artemissoftware.hermesreceipts.core.data.util.ImageConstants.IMAGE_EXTENSION
import java.io.File
import java.io.IOException

internal object ImageUtil {

    fun getImageName(prefix: String, time: String) = "$prefix$time.$IMAGE_EXTENSION"

    fun getFileFromUri(context: Context, uri: Uri): File? {
        val fileName = "receipt_${System.currentTimeMillis()}.jpg"
        val file = File(context.cacheDir, fileName)

        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun deleteImageByUri(context: Context, uri: Uri): Boolean {
        return try {
            context.contentResolver.delete(uri, null, null) > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}