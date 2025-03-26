package com.artemissoftware.hermesreceipts.core.domain.repository

import com.artemissoftware.hermesreceipts.core.domain.Resource

interface ImageRepository {
    suspend fun saveImage(image: ByteArray): Resource<String>
}