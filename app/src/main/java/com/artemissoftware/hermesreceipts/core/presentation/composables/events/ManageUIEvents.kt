package com.artemissoftware.hermesreceipts.core.presentation.composables.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ManageUIEvents(
    uiEvent: Flow<UiEvent>,
    onNavigate: (UiEvent.Navigate) -> Unit = {},
    onNavigateWithRoute: (UiEvent.NavigateWithRoute) -> Unit = {},
) {
    LaunchedEffect(key1 = Unit) {
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> { onNavigate(event) }
                is UiEvent.NavigateWithRoute -> { onNavigateWithRoute(event) }
            }
        }
    }
}