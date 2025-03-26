package com.artemissoftware.hermesreceipts.core.presentation.composables.events

sealed class UiEvent {
    data class NavigateWithRoute(val value: Any) : UiEvent()
    data class Navigate(val value: Any, val route: String = "") : UiEvent()
}