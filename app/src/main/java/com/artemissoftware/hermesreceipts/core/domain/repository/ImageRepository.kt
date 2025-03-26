package com.artemissoftware.hermesreceipts.core.domain.repository

import android.graphics.Bitmap
import com.artemissoftware.hermesreceipts.core.domain.Resource

interface ImageRepository {
    suspend fun saveImage(image: Bitmap): Resource<String>
}