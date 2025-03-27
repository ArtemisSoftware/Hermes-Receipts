package com.artemissoftware.hermesreceipts.feature.receipts.data.mapper

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.artemissoftware.hermesreceipts.testdata.DtoTestData.ocrDto
import com.artemissoftware.hermesreceipts.testdata.TestData.imagePath
import com.artemissoftware.hermesreceipts.testdata.TestData.receipt
import com.artemissoftware.hermesreceipts.testdata.TestData.receiptEntity
import org.junit.jupiter.api.Test

class ReceiptMapperTest {

    @Test
    fun `Map OcrDto to Receipt`() {
        assertThat(ocrDto.toReceipt(imagePath))
            .isEqualTo(receipt)
    }

    @Test
    fun `Map ReceiptEntity to Receipt`() {
        assertThat(receiptEntity.toReceipt())
            .isEqualTo(receipt)
    }

    @Test
    fun `Map Receipt to ReceiptEntity`() {
        assertThat(receipt.toEntity())
            .isEqualTo(receiptEntity)
    }
}