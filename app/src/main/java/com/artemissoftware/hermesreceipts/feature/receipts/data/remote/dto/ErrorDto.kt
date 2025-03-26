package com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto

import com.squareup.moshi.Json

data class ErrorDto(
    @field:Json(name = "success")
    val success: Boolean = false,
    @field:Json(name = "message")
    val message: String,

)
