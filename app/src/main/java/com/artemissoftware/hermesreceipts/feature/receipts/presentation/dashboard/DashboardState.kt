package com.artemissoftware.hermesreceipts.feature.receipts.presentation.dashboard

import com.artemissoftware.hermesreceipts.core.domain.models.Receipt

data class DashboardState(
    val isLoading: Boolean = false,
    val receipts: List<Receipt> = emptyList()
)
