package com.artemissoftware.hermesreceipts.feature.capture.presentation.capture

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEvent
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEventViewModel
import com.artemissoftware.hermesreceipts.core.presentation.util.extensions.toByteArray
import com.artemissoftware.hermesreceipts.feature.capture.domain.usecase.SaveImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel @Inject constructor(
    private val saveImageUseCase: SaveImageUseCase
) : UiEventViewModel() {

    private val _state = MutableStateFlow(CaptureState())
    val state = _state.asStateFlow()

    fun onTriggerEvent(event: CaptureEvent){
        when(event){
            is CaptureEvent.UpdateCapturedPhoto -> updateCapturedPhoto(bitmap = event.bitmap)
        }
    }

    private fun updateCapturedPhoto(bitmap: Bitmap) = with(_state) {
        cleanBitmap()
        update {
            it.copy(capturedBitmap = bitmap, isLoading = true)
        }
        saveImage()
    }

    private fun saveImage() {
        viewModelScope.launch {
            _state.value.capturedBitmap?.let { bitmap ->
                saveImageUseCase.invoke(bitmap.toByteArray())
                    .onSuccess { path ->
                        _state.update { it.copy(imagePath = path, isLoading = false) }
                        sendEvent(path)
                    }
                    .onFailure {
                        _state.update { it.copy(isLoading = false) }
                    }
            }
        }
    }

    private fun sendEvent(imagePath: String){
        viewModelScope.launch {
            sendUiEvent(
                UiEvent.Navigate(value = imagePath, route = "validate")
            )
        }
    }

    private fun cleanBitmap() = with(_state.value) {
        capturedBitmap?.recycle()
    }

    override fun onCleared() {
        cleanBitmap()
        super.onCleared()
    }
}