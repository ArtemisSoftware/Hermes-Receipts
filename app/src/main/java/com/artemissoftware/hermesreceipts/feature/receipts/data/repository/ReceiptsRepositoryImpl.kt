package com.artemissoftware.hermesreceipts.feature.receipts.data.repository

import android.content.Context
import android.net.Uri
import com.artemissoftware.hermesreceipts.R
import com.artemissoftware.hermesreceipts.core.data.database.dao.ReceiptDao
import com.artemissoftware.hermesreceipts.core.data.remote.HandleNetwork
import com.artemissoftware.hermesreceipts.core.data.util.ImageUtil
import com.artemissoftware.hermesreceipts.core.domain.Resource
import com.artemissoftware.hermesreceipts.core.domain.error.DataError
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import com.artemissoftware.hermesreceipts.feature.receipts.data.mapper.toEntity
import com.artemissoftware.hermesreceipts.feature.receipts.data.mapper.toReceipt
import com.artemissoftware.hermesreceipts.feature.receipts.data.remote.source.OcrApiSource
import com.artemissoftware.hermesreceipts.feature.receipts.domain.repository.ReceiptsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ReceiptsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val ocrApiSource: OcrApiSource,
    private val receiptDao: ReceiptDao
): ReceiptsRepository {

    override suspend fun scanReceipt(imagePath: String): Resource<Receipt> {

        val imageUri = Uri.parse(imagePath)
        val imageFile = ImageUtil.getFileFromUri(context = context, uri = imageUri)

        return imageFile?.let { file ->
            val body = createMultipartFromFile(file = file)
            val apiKey = createRequestBody("TEST")
            val recognizer = createRequestBody("auto")

            HandleNetwork.safeNetworkCall {
                ocrApiSource.scanReceipt(file = body, apiKey = apiKey, recognizer = recognizer).toReceipt(imagePath)
            }
        } ?: run {
            Resource.Failure(DataError.ImageError.Uri)
        }
    }

    override suspend fun deleteScan(imagePath: String): Resource<Unit> {
        val imageUri = Uri.parse(imagePath)
        val result = ImageUtil.deleteImageByUri(context, imageUri)
        return if (result){
            Resource.Success(Unit)
        }
        else Resource.Failure(DataError.ImageError.Error("Error deleting image"))
    }

    override suspend fun getReceipt(id: Int): Receipt? {
        return receiptDao.get(id)?.toReceipt()
    }

    override suspend fun getAllReceipt(): Flow<List<Receipt>> {
        return receiptDao.getAll().map { list -> list.map { it.toReceipt() }}
    }

    override suspend fun saveReceipt(receipt: Receipt) {
        receiptDao.insert(receipt.toEntity())
    }

    override suspend fun deleteReceipt(receipt: Receipt) {
        receiptDao.delete(receipt.id)
    }

    private fun createMultipartFromFile(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    private fun createRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}