package com.artemissoftware.hermesreceipts.feature.receipts.presentation.dashboard

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artemissoftware.hermesreceipts.R
import com.artemissoftware.hermesreceipts.core.designsystem.composables.scaffold.HRScaffold
import com.artemissoftware.hermesreceipts.core.designsystem.composables.topbar.HRTopBar
import com.artemissoftware.hermesreceipts.core.designsystem.spacing
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.ManageUIEvents
import com.artemissoftware.hermesreceipts.core.presentation.composables.permissions.rememberPermissionLauncher
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.composables.ReceiptCard
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation.ReceiptsRoute
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme
import java.time.LocalDate

@Composable
internal fun DashboardScreen(
    navigateToReceiptDetail:(Int) -> Unit,
    navigateToValidation:(String) -> Unit,
    navigateToCapture:() -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val requestPermission = rememberPermissionLauncher(
        permission = Manifest.permission.CAMERA,
        onPermissionGranted = navigateToCapture,
        onPermissionNotGranted = {}
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.onTriggerEvent(DashboardEvent.Save(it))
            }
        }
    )

    val state = viewModel.state.collectAsStateWithLifecycle().value

    DashboardContent(
        navigateToReceiptDetail = navigateToReceiptDetail,
        requestCameraPermission = { requestPermission() },
        openGallery = { galleryLauncher.launch("image/*") },
        state = state
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onNavigateWithRoute = {
            when(it.value){
                is ReceiptsRoute.Validation -> navigateToValidation(it.value.imagePath)
            }
        },
    )
}

@Composable
private fun DashboardContent(
    state: DashboardState,
    navigateToReceiptDetail:(Int) -> Unit,
    requestCameraPermission:() -> Unit,
    openGallery:() -> Unit
) {

    HRScaffold(
        isLoading = state.isLoading,
        topBar = {
            HRTopBar(
                title = stringResource(R.string.receipts),
                actions = {
                    IconButton(onClick = openGallery) {
                        Icon(
                            imageVector = Icons.Filled.ImageSearch,
                            contentDescription = ""
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = stringResource(R.string.take_a_photo),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                onClick = requestCameraPermission,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Camera capture icon"
                    )
                }
            )
        },
        content = {
            if(state.receipts.isEmpty()){
                EmptyReceipts()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.spacing2),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spacing1),
                ) {
                    items(
                        items = state.receipts,
                        key = { it.id }
                    ){ receipt ->
                        ReceiptCard(
                            onClick = {
                                navigateToReceiptDetail(it)
                            },
                            receipt = receipt,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun EmptyReceipts() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_receipts_available),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardContentPreview() {
    HermesReceiptsTheme {
        DashboardContent(
            navigateToReceiptDetail = {},
            requestCameraPermission = {},
            openGallery = {},
            state = DashboardState(
                receipts = listOf(
                    Receipt(
                        id = 0,
                        store = "Pet shop of horrors",
                        total = 25.50,
                        date = LocalDate.now(),
                        currency = "$",
                        imagePath = "image.jpg"
                    )
                )
            )
        )
    }
}