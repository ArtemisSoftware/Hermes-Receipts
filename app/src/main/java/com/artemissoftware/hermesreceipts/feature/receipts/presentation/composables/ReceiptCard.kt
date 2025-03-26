package com.artemissoftware.hermesreceipts.feature.receipts.presentation.composables

 import androidx.compose.foundation.Image
 import androidx.compose.foundation.clickable
 import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
 import androidx.compose.foundation.layout.size
 import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.draw.clip
 import androidx.compose.ui.layout.ContentScale
 import androidx.compose.ui.res.painterResource
 import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
 import androidx.compose.ui.unit.dp
 import com.artemissoftware.hermesreceipts.R
import com.artemissoftware.hermesreceipts.core.designsystem.spacing
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
 import com.artemissoftware.hermesreceipts.core.presentation.composables.description.FieldDescription
 import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme
import java.time.LocalDate

@Composable
internal fun ReceiptCard(
    receipt: Receipt,
    onClick:(Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(MaterialTheme.spacing.spacing1_5)
            .clickable { onClick(receipt.id) },
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spacing1_5),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(52.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
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


@Preview(showBackground = true)
@Composable
private fun ReceiptCardPreview() {
    HermesReceiptsTheme {
        ReceiptCard(
            onClick = {},
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