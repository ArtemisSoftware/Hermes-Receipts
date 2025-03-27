package com.artemissoftware.hermesreceipts.feature.receipts.domain.usecase

import com.artemissoftware.hermesreceipts.core.domain.repository.ImageRepository
import com.artemissoftware.hermesreceipts.feature.receipts.domain.repository.ReceiptsRepository
import javax.inject.Inject

class SaveImageFromUri @Inject constructor(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(imagePath: String) = imageRepository.saveImageAndGetUri(imagePath)
}