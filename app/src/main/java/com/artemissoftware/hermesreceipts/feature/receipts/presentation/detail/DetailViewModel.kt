package com.artemissoftware.hermesreceipts.feature.receipts.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.hermesreceipts.R
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEvent
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEventViewModel
import com.artemissoftware.hermesreceipts.core.presentation.util.UiText
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.DeleteReceiptUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.GetReceiptUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation.ReceiptsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val deleteReceiptUseCase: DeleteReceiptUseCase,
    private val getReceiptUseCase: GetReceiptUseCase,
    private val savedStateHandle: SavedStateHandle
): UiEventViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("id")?.let {
            getReceipt(id = it)
        } ?: run {
            sendError(UiText.StringResource(R.string.no_receipt_found))
        }
    }

    fun onTriggerEvent(event: DetailEvent){
        when(event){
            DetailEvent.Delete -> delete()
        }
    }

    private fun getReceipt(id: Int) = with(_state){
        viewModelScope.launch {
            val result = getReceiptUseCase(id)

            result?.let { receipt ->
                update { it.copy(receipt = receipt) }
            }
        }
    }

    private fun delete() = with(_state.value){
        viewModelScope.launch {
            receipt?.let {
                deleteReceiptUseCase(it)
            }
        }
    }

    private fun sendError(message: UiText) {
        viewModelScope.launch {
            sendUiEvent(UiEvent.Navigate(message, ReceiptsRoute.ERROR))
        }
    }
}