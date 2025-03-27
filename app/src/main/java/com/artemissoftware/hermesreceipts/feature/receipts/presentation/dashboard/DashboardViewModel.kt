package com.artemissoftware.hermesreceipts.feature.receipts.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.GetAllReceiptsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAllReceiptsUseCase: GetAllReceiptsUseCase
): ViewModel(){

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    init {
        getAllReceipts()
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
}