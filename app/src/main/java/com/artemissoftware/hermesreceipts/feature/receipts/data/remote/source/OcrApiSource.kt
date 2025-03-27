package com.artemissoftware.hermesreceipts.feature.receipts.data.remote.source

import com.artemissoftware.hermesreceipts.core.data.remote.HandleApi
import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.OcrApi
import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto.OcrDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class OcrApiSource @Inject constructor(
    private val ocrApi: OcrApi
) {
    suspend fun scanReceipt(
        file: MultipartBody.Part,
        apiKey: RequestBody,
        recognizer: RequestBody
    ): OcrDto {
        return HandleApi.safeApiCall { ocrApi.uploadReceipt(file = file, apiKey = apiKey, recognizer = recognizer) }
    }
}