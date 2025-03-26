package com.artemissoftware.hermesreceipts.core.presentation.composables.description

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme

@Composable
fun FieldDescription (
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
private fun FieldDescriptionPreview() {
    HermesReceiptsTheme {
        FieldDescription(
            title = "Pet shop of horrors",
            description = "image.jpg",
            modifier = Modifier.fillMaxWidth()
        )
    }
}