package com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto


import com.squareup.moshi.Json

data class OcrDto(
    @field:Json(name = "receipts")
    val receipts: List<ReceiptDto> = emptyList(),
    @field:Json(name = "success")
    val success: Boolean = false
)