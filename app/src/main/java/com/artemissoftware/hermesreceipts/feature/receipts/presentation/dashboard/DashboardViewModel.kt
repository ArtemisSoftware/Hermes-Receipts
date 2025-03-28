package com.artemissoftware.hermesreceipts.feature.receipts.presentation.dashboard

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.artemissoftware.hermesreceipts.core.domain.error.DataError
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEvent
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEventViewModel
import com.artemissoftware.hermesreceipts.core.presentation.util.UiText
import com.artemissoftware.hermesreceipts.core.presentation.util.extensions.toUiText
import com.artemissoftware.hermesreceipts.feature.capture.presentation.navigation.CaptureRoute
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.GetAllReceiptsUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.SaveImageFromUriUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation.ReceiptsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAllReceiptsUseCase: GetAllReceiptsUseCase,
    private val saveImageFromUriUseCase: SaveImageFromUriUseCase
): UiEventViewModel(){

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    init {
        getAllReceipts()
    }

    fun onTriggerEvent(event: DashboardEvent){
        when(event){
            is DashboardEvent.Save -> save(event.uri)
        }
    }

    private fun save(uri: Uri) {
        viewModelScope.launch {
            saveImageFromUriUseCase(uri.toString())
                .onSuccess {
                    sendEvent(it)
                }
                .onFailure { error ->
                    when(error){
                        DataError.ImageError.CreateImage -> sendError(error.toUiText())
                        is DataError.ImageError.Error -> sendError(error.toUiText())
                        else -> Unit
                    }
                }
        }
    }

    private fun getAllReceipts() = with(_state){
        viewModelScope.launch {
            getAllReceiptsUseCase().collectLatest { items ->
                update {
                    it.copy(receipts = items)
                }
            }
        }
    }

    private fun sendEvent(imagePath: String){
        viewModelScope.launch {
            sendUiEvent(
                UiEvent.NavigateWithRoute(ReceiptsRoute.Validation(imagePath))
            )
        }
    }

    private fun sendError(message: UiText) {
        viewModelScope.launch {
            sendUiEvent(UiEvent.Navigate(message, CaptureRoute.ERROR))
        }
    }
}