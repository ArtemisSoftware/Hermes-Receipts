package com.artemissoftware.hermesreceipts.testdata

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.artemissoftware.hermesreceipts.core.data.database.entity.ReceiptEntity
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.time.LocalDate

object TestData {

    const val imagePath = "images/image.jpg"

    val receipt = Receipt(
        imagePath = imagePath,
        store = "Modelo Continente Hipermercados S.A.",
        date = LocalDate.of(2023, 6, 9),
        currency = "EUR",
        total = 13.60
    )

    val receiptEntity = ReceiptEntity(
        id = 0,
        currency = "EUR",
        total = 13.60,
        store = "Modelo Continente Hipermercados S.A.",
        date = 1686268800000,
        imagePath = imagePath
    )

    private val file = File("path/to/your/file.jpg")
    private val fileRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)

    val apiKey = "your-api-key".toRequestBody("text/plain".toMediaTypeOrNull())
    val recognizer = "your-recognizer-value".toRequestBody("text/plain".toMediaTypeOrNull())

}