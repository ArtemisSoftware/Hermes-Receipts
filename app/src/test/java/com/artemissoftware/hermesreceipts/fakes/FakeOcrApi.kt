package com.artemissoftware.hermesreceipts.fakes

import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.OcrApi
import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto.OcrDto
import com.artemissoftware.hermesreceipts.testdata.DtoTestData
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeOcrApi : OcrApi {

    var shouldThrowError = false

    override suspend fun uploadReceipt(
        apiKey: RequestBody,
        recognizer: RequestBody,
        file: MultipartBody.Part
    ): OcrDto {
        if (shouldThrowError) throw Exception("Network error while fetching data")
        return DtoTestData.ocrDto
    }
}