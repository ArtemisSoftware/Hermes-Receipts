package com.artemissoftware.hermesreceipts.core.designsystem.composables.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.hermesreceipts.core.designsystem.dimension
import com.artemissoftware.hermesreceipts.core.designsystem.spacing
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme

@Composable
fun HRButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    backgroundColor: Color = Color.Unspecified
) {

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        onClick = onClick,
        modifier = modifier.heightIn(MaterialTheme.dimension.buttonHeight),
    ) {
        imageVector?.let {
            Icon(
                imageVector = it,
                contentDescription = "",
            )
            Spacer(Modifier.size(MaterialTheme.spacing.spacing1_5))
        }

        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ValidationContentPreview() {
    HermesReceiptsTheme {
        HRButton(
            onClick = {},
            text = "I am button",
            imageVector = Icons.Filled.Edit,
            modifier = Modifier.fillMaxWidth()
        )
    }
}