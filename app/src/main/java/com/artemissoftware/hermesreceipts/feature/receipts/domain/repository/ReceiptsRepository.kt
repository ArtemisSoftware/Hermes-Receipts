package com.artemissoftware.hermesreceipts.feature.receipts.domain.repository

import com.artemissoftware.hermesreceipts.core.domain.Resource
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import kotlinx.coroutines.flow.Flow

interface ReceiptsRepository {
    suspend fun scanReceipt(imagePath: String): Resource<Receipt>
    suspend fun deleteScan(imagePath: String): Resource<Unit>
    suspend fun getReceipt(id: Int): Receipt?
    suspend fun getAllReceipt(): Flow<List<Receipt>>
    suspend fun saveReceipt(receipt: Receipt)
    suspend fun deleteReceipt(receipt: Receipt)
}