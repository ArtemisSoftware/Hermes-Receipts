package com.artemissoftware.hermesreceipts.feature.receipts.presentation.detail

import com.artemissoftware.hermesreceipts.core.domain.models.Receipt

data class DetailState(
    val isLoading: Boolean = false,
    val receipt: Receipt? = null
)
