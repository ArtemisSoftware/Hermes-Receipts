package com.artemissoftware.hermesreceipts.feature.receipts.presentation.validation

import com.artemissoftware.hermesreceipts.core.domain.models.Receipt

data class ValidationState(
    val isLoading: Boolean = false,
    val receipt: Receipt? = null,
    val imagePath: String? = null
)
