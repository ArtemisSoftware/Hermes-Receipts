package com.artemissoftware.hermesreceipts.feature.dashboard.composables

 import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.hermesreceipts.R
import com.artemissoftware.hermesreceipts.core.designsystem.spacing
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme
import java.time.LocalDate

@Composable
internal fun ReceiptCard(
    receipt: Receipt,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(MaterialTheme.spacing.spacing1_5)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spacing1_5)
        ) {
            Description(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.store),
                description = receipt.store ?: stringResource(R.string.store_not_identified)
            )

            Description(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.total),
                description = receipt.totalPaid()
            )

            receipt.date?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = it.toString(),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@Composable
private fun Description (
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.labelMedium,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReceiptCardPreview() {
    HermesReceiptsTheme {
        ReceiptCard(
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