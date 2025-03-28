package com.artemissoftware.hermesreceipts.feature.receipts.presentation.dashboard

import android.net.Uri
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.UiEvent
import com.artemissoftware.hermesreceipts.fakes.FakeImageRepository
import com.artemissoftware.hermesreceipts.fakes.FakeReceiptRepository
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.GetAllReceiptsUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase.SaveImageFromUriUseCase
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation.ReceiptsRoute
import com.artemissoftware.hermesreceipts.testdata.TestData.receipt
import com.artemissoftware.hermesreceipts.util.MainCoroutineExtension
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
class DashboardViewModelTest {

    private lateinit var receiptRepository: FakeReceiptRepository
    private lateinit var imageRepository: FakeImageRepository
    private lateinit var getAllReceiptsUseCase: GetAllReceiptsUseCase
    private lateinit var saveImageFromUriUseCase: SaveImageFromUriUseCase

    private lateinit var viewModel: DashboardViewModel

    @BeforeEach
    fun setUp() {
        receiptRepository = FakeReceiptRepository()
        imageRepository = FakeImageRepository()
        getAllReceiptsUseCase = GetAllReceiptsUseCase(receiptRepository)
        saveImageFromUriUseCase = SaveImageFromUriUseCase(imageRepository)

        viewModel = DashboardViewModel(
            getAllReceiptsUseCase = getAllReceiptsUseCase,
            saveImageFromUriUseCase = saveImageFromUriUseCase
        )
    }

    @AfterEach
    fun teardown() {
        Dispatchers.resetMain()  // Reset after each test to avoid side effects
    }

    @org.junit.jupiter.api.Test
    fun `Get all receipts`() = runTest {

        advanceUntilIdle()

        viewModel.state.test {
            val emission1 = awaitItem()
            assertThat(emission1.receipts).isEqualTo(listOf(receipt))
        }
    }

    @org.junit.jupiter.api.Test
    fun `Save image with success`() = runTest {

        val uri: Uri = mockk(relaxed = true)

        viewModel.onTriggerEvent(DashboardEvent.Save(uri))
        advanceUntilIdle()

        viewModel.uiEvent.test {
            val emission1 = awaitItem() as UiEvent.NavigateWithRoute
            assertThat(emission1.value is ReceiptsRoute.Validation).isTrue()
        }
    }
}