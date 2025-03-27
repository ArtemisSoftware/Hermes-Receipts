package com.artemissoftware.hermesreceipts.core.data.repository

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.artemissoftware.hermesreceipts.core.data.util.ImageConstants.IMAGE_MIME_TYPE
import com.artemissoftware.hermesreceipts.core.data.util.ImageUtil.getImageName
import com.artemissoftware.hermesreceipts.core.domain.Resource
import com.artemissoftware.hermesreceipts.core.domain.error.DataError
import com.artemissoftware.hermesreceipts.core.domain.repository.ImageRepository
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val context: Context): ImageRepository {

    override suspend fun saveImage(image: ByteArray): Resource<String> {

        val bitmap = byteArrayToBitmap(image)
        val resolver: ContentResolver = context.applicationContext.contentResolver

        val imageCollection: Uri = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY)
            else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        // Publish a new image.
        val nowTimestamp: Long = System.currentTimeMillis()
        val imageContentValues: ContentValues = ContentValues().apply {

            put(MediaStore.Images.Media.DISPLAY_NAME, getImageName(IMAGE_FILE_PREFIX, nowTimestamp.toString()))
            put(MediaStore.Images.Media.MIME_TYPE, IMAGE_MIME_TYPE)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.DATE_TAKEN, nowTimestamp)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + IMAGE_DIRECTORY)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                put(MediaStore.Images.Media.DATE_TAKEN, nowTimestamp)
                put(MediaStore.Images.Media.DATE_ADDED, nowTimestamp)
                put(MediaStore.Images.Media.DATE_MODIFIED, nowTimestamp)
            }
        }

        val imageMediaStoreUri: Uri? = resolver.insert(imageCollection, imageContentValues)

        // Write the image data to the new Uri.
        val result = imageMediaStoreUri?.let { uri ->
            kotlin.runCatching {
                resolver.openOutputStream(uri).use { outputStream: OutputStream? ->
                    checkNotNull(outputStream) { "Couldn't create file for gallery, MediaStore output stream is null" }
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    imageContentValues.clear()
                    imageContentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, imageContentValues, null, null)
                }

                Resource.Success(uri.toString())
            }.getOrElse { exception: Throwable ->
                exception.message?.let(::println)
                resolver.delete(uri, null, null)
                Resource.Failure(DataError.ImageError.Error(exception.message))
            }
        } ?: run {
            Resource.Failure(DataError.ImageError.CreateImage)
        }

        return result
    }

    private fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    override suspend fun saveImageAndGetUri(imagePath: String): Resource<String> {
        val imageUri = Uri.parse(imagePath)
        val nowTimestamp: Long = System.currentTimeMillis()
        return try {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, getImageName(IMAGE_FILE_PREFIX, nowTimestamp.toString()))
                put(MediaStore.Images.Media.MIME_TYPE, IMAGE_MIME_TYPE)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + IMAGE_DIRECTORY)
            }

            // Insert into MediaStore and get the new file URI
            val newImageUri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            newImageUri?.let { uri ->
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }

            Resource.Success(newImageUri.toString())
        } catch (e: Exception) {
            Resource.Failure(DataError.ImageError.Error(e.message))
        }
    }


    private companion object {
        const val IMAGE_FILE_PREFIX = "receipt_"
        const val IMAGE_DIRECTORY = "/receipts"
    }
}