package com.artemissoftware.hermesreceipts.testdata

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.artemissoftware.hermesreceipts.core.data.database.entity.ReceiptEntity
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
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
}