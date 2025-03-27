package com.artemissoftware.hermesreceipts.feature.receipts.presentation.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEvent
import com.artemissoftware.hermesreceipts.fakes.FakeReceiptRepository
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.DeleteReceiptUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.GetReceiptUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation.ReceiptsRoute
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
class DetailViewModelTest {

    private lateinit var receiptRepository: FakeReceiptRepository
    private lateinit var getReceiptUseCase: GetReceiptUseCase
    private lateinit var deleteReceiptUseCase: DeleteReceiptUseCase
    private lateinit var viewModel: DetailViewModel

    private lateinit var savedStateHandle: SavedStateHandle

    @BeforeEach
    fun setUp() {
        receiptRepository = FakeReceiptRepository()
        getReceiptUseCase = GetReceiptUseCase(receiptRepository)
        deleteReceiptUseCase = DeleteReceiptUseCase(receiptRepository)

        savedStateHandle = mockk(relaxed = true)

        every { savedStateHandle.get<Int>("id") } returns 1

        viewModel = DetailViewModel(
            getReceiptUseCase = getReceiptUseCase,
            deleteReceiptUseCase = deleteReceiptUseCase,
            savedStateHandle = savedStateHandle
        )
    }

    @AfterEach
    fun teardown() {
        Dispatchers.resetMain()  // Reset after each test to avoid side effects
    }

    @org.junit.jupiter.api.Test
    fun `Get receipt from navigation argument`() = runTest {
        advanceUntilIdle()

        assertThat(viewModel.state.value.receipt).isEqualTo(receipt)
    }

    @org.junit.jupiter.api.Test
    fun `Get error when receipt is not found`() = runTest {
        every { savedStateHandle.get<Int>("id") } returns null

        val viewModel = DetailViewModel(
            getReceiptUseCase = getReceiptUseCase,
            deleteReceiptUseCase = deleteReceiptUseCase,
            savedStateHandle = savedStateHandle
        )

        advanceUntilIdle()

        viewModel.uiEvent.test {
            val emission1 = (awaitItem() as UiEvent.Navigate)
            assertThat(emission1.route).isEqualTo(ReceiptsRoute.ERROR)
        }

    }
}