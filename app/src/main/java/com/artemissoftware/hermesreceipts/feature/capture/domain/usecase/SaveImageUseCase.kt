package com.artemissoftware.hermesreceipts.feature.capture.domain.usecase

import com.artemissoftware.hermesreceipts.core.domain.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(image: ByteArray) = withContext(Dispatchers.IO) { return@withContext imageRepository.saveImage(image) }
}