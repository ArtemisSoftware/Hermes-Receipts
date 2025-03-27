package com.artemissoftware.hermesreceipts.feature.receipts.presentation.validation

sealed interface ValidationEvent {
    data object Delete: ValidationEvent
    data object Save: ValidationEvent
}