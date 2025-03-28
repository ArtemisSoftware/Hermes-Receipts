package com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.dashboard.DashboardScreen
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.detail.DetailScreen
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.validation.ValidationScreen

fun NavGraphBuilder.receiptsNavGraph(
    navigateToError: (String) -> Unit,
    navigateToCapture: () -> Unit,
    navController: NavHostController
) {

    composable<ReceiptsRoute.Dashboard> {
        DashboardScreen(
            navigateToReceiptDetail = {
                navController.navigate(ReceiptsRoute.Detail(it))
            },
            navigateToValidation = {
                navController.navigate(ReceiptsRoute.Validation(it))
            },
            navigateToCapture = navigateToCapture,
            navigateToError = navigateToError
        )
    }

    composable<ReceiptsRoute.Validation> {
        ValidationScreen(
            navigateToError = navigateToError,
            navigateBack = { navController.popBackStack() },
        )
    }

    composable<ReceiptsRoute.Detail> {
        DetailScreen(
            navigateToError = navigateToError,
            navigateBack = { navController.popBackStack() },
        )
    }
}