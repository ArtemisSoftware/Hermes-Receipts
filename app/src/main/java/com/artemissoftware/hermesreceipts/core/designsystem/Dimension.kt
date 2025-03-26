package com.artemissoftware.hermesreceipts.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

data class Dimension(
    val iconSizeSmall: DpSize,
    val iconSize: DpSize,
    val iconSizeMedium: DpSize,
    val iconSizeBig: DpSize,
    val iconSizeExtraBig: DpSize,
    val timerBannerHeight: Dp,
)

val dimensionPortrait = Dimension(
    iconSizeSmall = DpSize(width = 16.dp, height = 16.dp),
    iconSize = DpSize(width = 24.dp, height = 24.dp),
    iconSizeMedium = DpSize(width = 40.dp, height = 40.dp),
    iconSizeBig = DpSize(width = 60.dp, height = 60.dp),
    iconSizeExtraBig = DpSize(width = 100.dp, height = 100.dp),
    timerBannerHeight = 140.dp
)

internal val localDimension = staticCompositionLocalOf<Dimension> { throw IllegalStateException("No theme installed") }

val MaterialTheme.dimension: Dimension
    @Composable
    get() = localDimension.current