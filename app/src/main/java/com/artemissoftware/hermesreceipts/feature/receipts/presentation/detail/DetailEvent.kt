package com.artemissoftware.hermesreceipts.feature.receipts.presentation.detail

sealed interface DetailEvent {
    data object Delete: DetailEvent
}