package com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ReceiptDto(
    @field:Json(name = "currency")
    val currency: String = "",
    @field:Json(name = "date")
    val date: String = "",
    @field:Json(name = "merchant_name")
    val merchantName: String = "",
    @field:Json(name = "total")
    val total: Double = 0.0,
)