package com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation

import kotlinx.serialization.Serializable

sealed class ReceiptsRoute {

    @Serializable
    data object Dashboard

    @Serializable
    data class Validation(val imagePath: String)

    @Serializable
    data class Detail(val id: Int)

    companion object {
        const val ERROR = "error"
    }
}