package com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ReceiptDto(
    @field:Json(name = "currency")
    val currency: String? = null,
    @field:Json(name = "date")
    val date: String? = null,
    @field:Json(name = "merchant_name")
    val merchantName: String? = null,
    @field:Json(name = "total")
    val total: Double? = null,
)