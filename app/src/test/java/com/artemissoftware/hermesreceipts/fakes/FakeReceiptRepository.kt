package com.artemissoftware.hermesreceipts.fakes

import com.artemissoftware.hermesreceipts.core.domain.Resource
import com.artemissoftware.hermesreceipts.core.domain.error.DataError
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import com.artemissoftware.hermesreceipts.feature.receipts.domain.repository.ReceiptsRepository
import com.artemissoftware.hermesreceipts.testdata.TestData.receipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeReceiptRepository: ReceiptsRepository {

    var showScanError = false
    private val data = mutableListOf(receipt)

    override suspend fun scanReceipt(imagePath: String): Resource<Receipt> {
        if(showScanError){
            return Resource.Success(receipt)
        } else {
            return Resource.Failure(DataError.NetworkError.Unknown)
        }
    }

    override suspend fun deleteScan(imagePath: String): Resource<Unit> {
        return Resource.Success(Unit)
    }

    override suspend fun getReceipt(id: Int): Receipt? {
        return data.first()
    }

    override suspend fun getAllReceipt(): Flow<List<Receipt>> {
        return flowOf(data)
    }

    override suspend fun saveReceipt(receipt: Receipt) {
        data.add(receipt)
    }

    override suspend fun deleteReceipt(receipt: Receipt) {
        data.removeFirst()
    }
}