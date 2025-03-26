package com.artemissoftware.hermesreceipts.feature.receipts.data.remote

import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.dto.OcrDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OcrApi {
    @Multipart
    @POST("api/v1/receipt")
    suspend fun uploadReceipt(
        @Part body: MultipartBody.Part,
        @Part file: MultipartBody.Part
    ): OcrDto

    companion object {
        const val BASE_URL = "https://ocr.asprise.com/"
    }
}