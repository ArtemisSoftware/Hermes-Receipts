package com.artemissoftware.hermesreceipts.feature.receipts.data.mapper

import com.artemissoftware.hermesreceipts.core.data.database.entity.ReceiptEntity
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import com.artemissoftware.hermesreceipts.core.domain.util.extensions.toEpochMillis
import com.artemissoftware.hermesreceipts.core.domain.util.extensions.toLocalDate
import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto.OcrDto
import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto.ReceiptDto

internal fun OcrDto.toReceipt(imagePath: String): Receipt {
    val receipt = this.receipts.first()
    return receipt.toReceipt(imagePath)
}

private fun ReceiptDto.toReceipt(imagePath: String): Receipt {
    return Receipt(
        imagePath = imagePath,
        store = merchantName,
        total = total,
        currency = currency,
        date = date.toLocalDate()
    )
}

internal fun ReceiptEntity.toReceipt(): Receipt {
    return Receipt(
        id = id,
        imagePath = imagePath,
        store = store,
        total = total,
        currency = currency,
        date = date!!.toLocalDate()
    )
}

internal fun Receipt.toEntity(): ReceiptEntity {
    return ReceiptEntity(
        imagePath = imagePath,
        store = store,
        total = total,
        currency = currency,
        date = date!!.toEpochMillis(),
        id = id
    )
}
