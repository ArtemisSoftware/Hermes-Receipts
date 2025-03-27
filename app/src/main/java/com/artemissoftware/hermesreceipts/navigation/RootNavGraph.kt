package com.artemissoftware.hermesreceipts.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.artemissoftware.hermesreceipts.core.presentation.screen.ErrorScreen
import com.artemissoftware.hermesreceipts.feature.capture.presentation.navigation.CaptureRoute
import com.artemissoftware.hermesreceipts.feature.capture.presentation.navigation.captureNavGraph
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation.ReceiptsRoute
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation.receiptsNavGraph

@Composable
fun RootNavGraph(
    navController: NavHostController,
    startDestination: Any = ReceiptsRoute.Dashboard,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        receiptsNavGraph(
            navController = navController,
            navigateToError = {
                navController.navigate(RootRoute.Error(it))
            },
            navigateToCapture = {
                navController.navigate(CaptureRoute.Capture)
            }
        )

        captureNavGraph(
            navController = navController,
            navigateToError = {
                navController.navigate(RootRoute.Error(it))
            },
            navigateToValidation = {
                navController.navigate(ReceiptsRoute.Validation(it)) {
                    popUpTo(CaptureRoute.Capture) { inclusive = true }
                }
            }
        )

        composable<RootRoute.Error> {
            val route = it.toRoute<RootRoute.Error>()
            ErrorScreen(
                errorMessage = route.message,
                onConfirm = {
                    navController.navigate(ReceiptsRoute.Dashboard) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}