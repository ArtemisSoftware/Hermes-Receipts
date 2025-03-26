package com.artemissoftware.hermesreceipts.feature.capture.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.artemissoftware.hermesreceipts.feature.capture.presentation.capture.CaptureScreen

fun NavGraphBuilder.captureNavGraph(
    navigateToValidation:(String) -> Unit,
    navController: NavHostController
) {

    composable<CaptureRoute.Capture> {
        CaptureScreen(
            navigateToValidation = navigateToValidation
        )
    }
}