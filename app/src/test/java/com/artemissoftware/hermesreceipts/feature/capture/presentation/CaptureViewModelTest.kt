package com.artemissoftware.hermesreceipts.feature.capture.presentation

import android.graphics.Bitmap
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.artemissoftware.hermesreceipts.fakes.FakeImageRepository
import com.artemissoftware.hermesreceipts.feature.capture.domain.usecase.SaveImageUseCase
import com.artemissoftware.hermesreceipts.feature.capture.presentation.capture.CaptureEvent
import com.artemissoftware.hermesreceipts.feature.capture.presentation.capture.CaptureViewModel
import com.artemissoftware.hermesreceipts.util.MainCoroutineExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
class CaptureViewModelTest {

    private lateinit var imageRepository: FakeImageRepository

    private lateinit var viewModel: CaptureViewModel

    @BeforeEach
    fun setUp() {
        imageRepository = FakeImageRepository()
        viewModel = CaptureViewModel(SaveImageUseCase(imageRepository))
    }

    @Test
    fun `Update captured image`() = runTest {

        val bitmap: Bitmap = mockk(relaxed = true)
        viewModel.onTriggerEvent(CaptureEvent.UpdateCapturedPhoto(bitmap))

        val state = viewModel.state.value
        assertThat(bitmap).isEqualTo(state.capturedBitmap)
    }
}