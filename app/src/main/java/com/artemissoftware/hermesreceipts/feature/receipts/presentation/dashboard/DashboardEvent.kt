package com.artemissoftware.hermesreceipts.feature.receipts.presentation.dashboard

import android.net.Uri

sealed interface DashboardEvent {
    data class Save(val uri: Uri): DashboardEvent
}