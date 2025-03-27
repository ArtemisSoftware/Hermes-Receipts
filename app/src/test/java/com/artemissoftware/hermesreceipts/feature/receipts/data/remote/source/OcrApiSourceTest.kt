package com.artemissoftware.hermesreceipts.feature.receipts.data.remote.source

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.artemissoftware.hermesreceipts.fakes.FakeOcrApi
import com.artemissoftware.hermesreceipts.testdata.DtoTestData
import com.artemissoftware.hermesreceipts.testdata.TestData.apiKey
import com.artemissoftware.hermesreceipts.testdata.TestData.filePart
import com.artemissoftware.hermesreceipts.testdata.TestData.recognizer
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class OcrApiSourceTest  {

    private lateinit var fakeOcrApi: FakeOcrApi
    private lateinit var ocrApiSource: OcrApiSource

    @BeforeEach
    fun setUp() {
        fakeOcrApi = FakeOcrApi()
        ocrApiSource = OcrApiSource(fakeOcrApi)
    }

    @Test
    fun `Scan receipt return OcrDto`() = runBlocking {

        val result = ocrApiSource.scanReceipt(file = filePart, apiKey = apiKey, recognizer = recognizer)

        assertThat(DtoTestData.ocrDto)
            .isEqualTo(result)
    }

    @Test
    fun `Scan receipt should throw error when API fails`() = runTest {

        fakeOcrApi.shouldThrowError = true

        assertFailure {
            ocrApiSource.scanReceipt(file = filePart, apiKey = apiKey, recognizer = recognizer)
        }
    }

}