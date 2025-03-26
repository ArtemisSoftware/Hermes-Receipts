package com.artemissoftware.hermesreceipts.core.designsystem.composables.button

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.artemissoftware.hermesreceipts.core.designsystem.dimension
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme

@Composable
fun HRCircularIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: DpSize = MaterialTheme.dimension.iconSizeMedium,
    backgroundColor: Color = Color.Gray,
    contentColor: Color = Color.White
) {
    Surface(
        shape = CircleShape,
        color = backgroundColor,
        modifier = modifier.size(size),
        shadowElevation = 8.dp
    ) {
        IconButton(onClick = onClick) {
            Icon(imageVector = icon, contentDescription = null, tint = contentColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HRCircularIconButtonPreview() {
    HermesReceiptsTheme {
        HRCircularIconButton(
            icon = Icons.Default.Favorite,
            onClick = { },
            backgroundColor = Color.Red,
            contentColor = Color.White
        )
    }
}