package com.artemissoftware.hermesreceipts.feature.receipts.data.repository

import android.content.Context
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.artemissoftware.hermesreceipts.core.data.database.dao.ReceiptDao
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import com.artemissoftware.hermesreceipts.fakes.FakeOcrApi
import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.source.OcrApiSource
import com.artemissoftware.hermesreceipts.testdata.TestData.receipt
import com.artemissoftware.hermesreceipts.testdata.TestData.receiptEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class ReceiptsRepositoryImplTest {

    private lateinit var repository: ReceiptsRepositoryImpl
    private lateinit var ocrApi: FakeOcrApi
    private lateinit var ocrApiSource: OcrApiSource
    private lateinit var receiptDao: ReceiptDao
    private lateinit var context: Context

    @BeforeEach
    fun setUp() {
        context = mockk()
        receiptDao = mockk()
        ocrApi = FakeOcrApi()
        ocrApiSource =  OcrApiSource(ocrApi = ocrApi)
        repository = ReceiptsRepositoryImpl(ocrApiSource = ocrApiSource, receiptDao = receiptDao, context = context)
    }


    @Test
    fun `Get receipt returns receipt`() = runBlocking {

        coEvery { receiptDao.get(any()) } returns receiptEntity

        val result = repository.getReceipt(1)

        coVerify { receiptDao.get(any()) }

        assertThat(receipt).isEqualTo(result)
    }

    @Test
    fun `Get all receipts returns receipts`() = runBlocking {

        var receipts: List<Receipt> = emptyList()
        coEvery { receiptDao.getAll() } returns flowOf(listOf(receiptEntity))

        val result = repository.getAllReceipt()
        result.collect { receipts = it }

        coVerify(exactly = 1) { receiptDao.getAll() }

        assertThat(receipts).isEqualTo(listOf(receipt))
    }

}