package com.artemissoftware.hermesreceipts.fakes

import com.artemissoftware.hermesreceipts.core.domain.Resource
import com.artemissoftware.hermesreceipts.core.domain.error.DataError
import com.artemissoftware.hermesreceipts.core.domain.repository.ImageRepository
import com.artemissoftware.hermesreceipts.testdata.TestData
import com.artemissoftware.hermesreceipts.testdata.TestData.imagePath

class FakeImageRepository: ImageRepository {

    var returnError = false

    override suspend fun saveImage(image: ByteArray): Resource<String> {
        if(returnError){
            return Resource.Failure(DataError.ImageError.Error("error"))
        }
        else {
            return Resource.Success(imagePath)
        }
    }

    override suspend fun saveImageAndGetUri(imagePath: String): Resource<String> {
        if(returnError){
            return Resource.Failure(DataError.ImageError.Error("error"))
        }
        else {
            return Resource.Success(TestData.imagePath)
        }
    }
}