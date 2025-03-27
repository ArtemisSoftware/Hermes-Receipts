package com.artemissoftware.hermesreceipts.feature.receipts.presentation.composables

 import androidx.compose.foundation.clickable
 import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
 import androidx.compose.foundation.layout.size
 import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material3.Card
 import androidx.compose.material3.CardDefaults
 import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.draw.clip
 import androidx.compose.ui.layout.ContentScale
 import androidx.compose.ui.platform.LocalContext
 import androidx.compose.ui.res.stringResource
 import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
 import androidx.compose.ui.unit.dp
 import coil.compose.AsyncImage
 import coil.request.ImageRequest
 import com.artemissoftware.hermesreceipts.R
 import com.artemissoftware.hermesreceipts.core.designsystem.dimension
 import com.artemissoftware.hermesreceipts.core.designsystem.shape
 import com.artemissoftware.hermesreceipts.core.designsystem.spacing
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
 import com.artemissoftware.hermesreceipts.core.presentation.composables.description.FieldDescription
 import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme
 import com.artemissoftware.hermesreceipts.ui.theme.Blue10
 import java.time.LocalDate

@Composable
internal fun ReceiptCard(
    receipt: Receipt,
    onClick:(Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val request = ImageRequest
        .Builder(LocalContext.current)
        .data(receipt.imagePath)
        .error(R.drawable.ic_launcher_foreground)
        .placeholder(R.drawable.ic_launcher_foreground)
        .crossfade(true)

    Card(
        modifier = modifier
            .clip(MaterialTheme.shape.noCorners)
            .clickable { onClick(receipt.id) },
        colors = CardDefaults.cardColors(
            containerColor = Blue10
        ),
        shape = MaterialTheme.shape.noCorners
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.spacing2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spacing3),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                modifier = Modifier
                    .size(MaterialTheme.dimension.imageCard)
                    .clip(RoundedCornerShape(10.dp)),
                model = request
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
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