package com.artemissoftware.hermesreceipts.feature.receipts.presentation.validation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEvent
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEventViewModel
import com.artemissoftware.hermesreceipts.core.presentation.util.UiText
import com.artemissoftware.hermesreceipts.core.presentation.util.extensions.toUiText
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.DeleteScanUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.SaveReceiptUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.ScanImageUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation.ReceiptsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidationViewModel @Inject constructor(
    private val saveReceiptUseCase: SaveReceiptUseCase,
    private val scanImageUseCase: ScanImageUseCase,
    private val deleteScanUseCase: DeleteScanUseCase,
    private val savedStateHandle: SavedStateHandle
) : UiEventViewModel() {

    private val _state = MutableStateFlow(ValidationState())
    val state = _state.asStateFlow()

    init {
        val imagePath = savedStateHandle.get<String>("imagePath").orEmpty()
        scanImage(imagePath)
    }

    fun onTriggerEvent(event: ValidationEvent){
        when(event){
            ValidationEvent.Delete -> delete()
            ValidationEvent.Save -> save()
        }
    }

    private fun scanImage(imagePath: String) = with(_state) {

        update {
            it.copy(isLoading = true, imagePath = imagePath)
        }

        viewModelScope.launch {
            scanImageUseCase(imagePath = imagePath)
                .onSuccess { receipt ->
                    update {
                        it.copy(isLoading = false, receipt = receipt)
                    }
                }
                .onFailure { error ->
                    update {
                        it.copy(isLoading = false)
                    }

                    sendError(error.toUiText())
                }
        }
    }


    private fun save() = with(_state){
        viewModelScope.launch {
            value.receipt?.let { receipt ->
                saveReceiptUseCase(receipt)
                sendEvent()
            }
        }
    }

    private fun delete() = with(_state){
        viewModelScope.launch {
            value.imagePath?.let { path ->
                deleteScanUseCase(path)
                    .onSuccess {
                        sendEvent()
                    }
                    .onFailure {
                        update {
                            it.copy(isLoading = false)
                        }
                    }
            }
        }
    }

    private fun sendEvent(){
        viewModelScope.launch {
            sendUiEvent(
                UiEvent.NavigateWithRoute(ReceiptsRoute.Dashboard)
            )
        }
    }

    private fun sendError(uiText: UiText) {
        viewModelScope.launch {
            sendUiEvent(
                UiEvent.Navigate(uiText, ReceiptsRoute.ERROR)
            )
        }
    }
}