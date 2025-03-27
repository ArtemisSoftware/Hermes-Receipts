package com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase

import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import com.artemissoftware.hermesreceipts.feature.receipts.domain.repository.ReceiptsRepository
import javax.inject.Inject

class DeleteReceiptUseCase @Inject constructor(private val receiptsRepository: ReceiptsRepository) {
    suspend operator fun invoke(receipt: Receipt) = receiptsRepository.deleteReceipt(receipt)
}