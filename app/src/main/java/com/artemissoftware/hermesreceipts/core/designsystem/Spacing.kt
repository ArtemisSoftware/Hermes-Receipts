package com.artemissoftware.hermesreceipts.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val spacing0_5: Dp,
    val spacing1: Dp,
    val spacing1_5: Dp,
    val spacing2: Dp,
    val spacing3: Dp,
    val spacing4: Dp,
    val spacing10: Dp,
)

internal val spacing = Spacing(
    spacing0_5 = 4.dp,
    spacing1 = 8.dp,
    spacing1_5 = 12.dp,
    spacing2 = 16.dp,
    spacing3 = 24.dp,
    spacing4 = 32.dp,
    spacing10 = 80.dp,
)

internal val localSpacing = staticCompositionLocalOf<Spacing> { throw IllegalStateException("No theme installed") }

val MaterialTheme.spacing: Spacing
    @Composable
    get() = localSpacing.current