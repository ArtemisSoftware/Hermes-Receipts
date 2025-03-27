package com.artemissoftware.hermesreceipts.testdata

import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto.OcrDto
import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto.ReceiptDto

object DtoTestData {

    val receiptDto = ReceiptDto(
        currency = "EUR",
        date = "09/06/2023",
        merchantName = "Modelo Continente Hipermercados S.A.",
        total = 13.60
    )

    val ocrDto = OcrDto(
        receipts = listOf(receiptDto),
        success = true
    )
}