package com.artemissoftware.hermesreceipts.feature.receipts.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.artemissoftware.hermesreceipts.feature.capture.CameraState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(): ViewModel(){

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()
}