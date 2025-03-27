package com.artemissoftware.hermesreceipts.feature.receipts.presentation.validation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEvent
import com.artemissoftware.hermesreceipts.fakes.FakeReceiptRepository
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.DeleteScanUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.GetAllReceiptsUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.SaveReceiptUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.ScanImageUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.dashboard.DashboardViewModel
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation.ReceiptsRoute
import com.artemissoftware.hermesreceipts.testdata.TestData.imagePath
import com.artemissoftware.hermesreceipts.testdata.TestData.receipt
import com.artemissoftware.hermesreceipts.util.MainCoroutineExtension
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
class ValidationViewModelTest {

    private lateinit var receiptRepository: FakeReceiptRepository
    private lateinit var scanImageUseCase: ScanImageUseCase
    private lateinit var deleteScanUseCase: DeleteScanUseCase
    private lateinit var saveReceiptUseCase: SaveReceiptUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: ValidationViewModel

    @BeforeEach
    fun setUp() {
        receiptRepository = FakeReceiptRepository()
        scanImageUseCase = ScanImageUseCase(receiptRepository)
        deleteScanUseCase = DeleteScanUseCase(receiptRepository)
        saveReceiptUseCase = SaveReceiptUseCase(receiptRepository)
        savedStateHandle = mockk(relaxed = true)

        every { savedStateHandle.get<String>("imagePath") } returns imagePath

        viewModel = ValidationViewModel(
            scanImageUseCase = scanImageUseCase,
            savedStateHandle = savedStateHandle,
            deleteScanUseCase = deleteScanUseCase,
            saveReceiptUseCase = saveReceiptUseCase
        )
    }

    @AfterEach
    fun teardown() {
        Dispatchers.resetMain()  // Reset after each test to avoid side effects
    }

    @org.junit.jupiter.api.Test
    fun `Scan receipt return receipt`() = runTest {
        advanceUntilIdle()

        viewModel.state.test {
            val emission1 = awaitItem()
            assertThat(emission1.receipt).isEqualTo(receipt)
        }
    }

    @org.junit.jupiter.api.Test
    fun `Scan receipt return error`() = runTest {
        receiptRepository.showScanError = true
        advanceUntilIdle()

        viewModel.uiEvent.test {
            val emission1 = (awaitItem() as UiEvent.Navigate)
            assertThat(emission1.route).isEqualTo(ReceiptsRoute.ERROR)
        }
    }

}