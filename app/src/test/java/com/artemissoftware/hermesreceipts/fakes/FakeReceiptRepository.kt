package com.artemissoftware.hermesreceipts.fakes

import com.artemissoftware.hermesreceipts.core.domain.Resource
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import com.artemissoftware.hermesreceipts.feature.receipts.domain.repository.ReceiptsRepository
import com.artemissoftware.hermesreceipts.testdata.TestData.receipt
import kotlinx.coroutines.flow.Flow

class FakeReceiptRepository: ReceiptsRepository {

    private val data = mutableListOf(receipt)

    override suspend fun scanReceipt(imagePath: String): Resource<Receipt> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteScan(imagePath: String): Resource<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getReceipt(id: Int): Receipt? {
        return data.first()
    }

    override suspend fun getAllReceipt(): Flow<List<Receipt>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveReceipt(receipt: Receipt) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReceipt(receipt: Receipt) {
        data.removeFirst()
    }
}