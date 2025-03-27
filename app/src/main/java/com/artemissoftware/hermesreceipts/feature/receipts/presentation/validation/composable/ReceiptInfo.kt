package com.artemissoftware.hermesreceipts.feature.receipts.presentation.validation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.hermesreceipts.R
import com.artemissoftware.hermesreceipts.core.designsystem.spacing
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import com.artemissoftware.hermesreceipts.core.presentation.composables.description.FieldDescription
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme
import java.time.LocalDate

@Composable
internal fun ReceiptInfo(
    receipt: Receipt,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spacing1_5)
    ) {
        FieldDescription(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.store),
            description = receipt.store ?: stringResource(R.string.store_not_identified)
        )

        FieldDescription(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.total),
            description = receipt.totalPaid()
        )

        FieldDescription(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.date),
            description = receipt.date.toString()
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun FieldDescriptionPreview() {
    HermesReceiptsTheme {
        ReceiptInfo(
            receipt = Receipt(
                id = 0,
                store = "Pet shop of horrors",
                total = 25.50,
                date = LocalDate.now(),
                currency = "$",
                imagePath = "image.jpg"
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}