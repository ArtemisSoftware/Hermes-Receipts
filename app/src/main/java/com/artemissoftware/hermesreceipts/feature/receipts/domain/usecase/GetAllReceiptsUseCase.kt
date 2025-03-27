package com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase

import com.artemissoftware.hermesreceipts.feature.receipts.domain.repository.ReceiptsRepository
import javax.inject.Inject

class GetAllReceiptsUseCase @Inject constructor(private val receiptsRepository: ReceiptsRepository) {
    suspend operator fun invoke() = receiptsRepository.getAllReceipt()
}