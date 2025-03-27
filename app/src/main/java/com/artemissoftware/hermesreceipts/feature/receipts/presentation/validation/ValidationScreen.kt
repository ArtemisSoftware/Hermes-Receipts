package com.artemissoftware.hermesreceipts.feature.receipts.presentation.validation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.artemissoftware.hermesreceipts.R
import com.artemissoftware.hermesreceipts.core.designsystem.composables.button.HRButton
import com.artemissoftware.hermesreceipts.core.designsystem.composables.scaffold.HRScaffold
import com.artemissoftware.hermesreceipts.core.designsystem.composables.topbar.HRTopBar
import com.artemissoftware.hermesreceipts.core.designsystem.dimension
import com.artemissoftware.hermesreceipts.core.designsystem.shape
import com.artemissoftware.hermesreceipts.core.designsystem.spacing
import com.artemissoftware.hermesreceipts.core.domain.models.Receipt
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.ManageUIEvents
import com.artemissoftware.hermesreceipts.core.presentation.util.UiText
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.navigation.ReceiptsRoute
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.validation.composable.ReceiptInfo
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme
import java.time.LocalDate

@Composable
fun ValidationScreen(
    navigateToError: (String) -> Unit,
    navigateBack: () -> Unit,
    viewModel: ValidationViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle().value
    ValidationContent(
        navigateBack = navigateBack,
        state = state,
        onEvent = viewModel::onTriggerEvent
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onNavigateWithRoute = {
            when(it.value){
                is ReceiptsRoute.Dashboard -> navigateBack()
            }
        },
        onNavigate = {
            when(it.route){
                ReceiptsRoute.ERROR ->  navigateToError((it.value as UiText).asString(context))
                else -> Unit
            }
        },
    )
}

@Composable
private fun ValidationContent(
    navigateBack: () -> Unit,
    state: ValidationState,
    onEvent: (ValidationEvent) -> Unit
) {

    var request = ImageRequest
        .Builder(LocalContext.current)
        .placeholder(R.drawable.ic_placeholder)
        .crossfade(true)

    state.imagePath?.let {
        request = request
            .data(it)
            .error(R.drawable.ic_broken)
    }

    HRScaffold(
        isLoading = state.isLoading,
        topBar = {
            HRTopBar(
                title = stringResource(R.string.receipt_scan),
                onBackClick = navigateBack
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    modifier = Modifier
                        .size(MaterialTheme.dimension.imageDetail)
                        .clip(MaterialTheme.shape.roundedCorners1_5),
                    model = request
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.spacing3))

                state.receipt?.let{
                    ReceiptInfo(
                        receipt = it,
                        modifier = Modifier.fillMaxWidth(0.5F)
                    )
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.spacing4))

                HRButton(
                    text = "Save",
                    imageVector = Icons.Default.Save,
                    onClick = {
                        onEvent(ValidationEvent.Save)
                    },
                    modifier = Modifier.fillMaxWidth(0.8F)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ValidationContentPreview() {
    HermesReceiptsTheme {
        ValidationContent(
            navigateBack = {},
            onEvent = {},
            state = ValidationState(
                receipt = Receipt(
                    id = 0,
                    store = "Pet shop of horrors",
                    total = 25.50,
                    date = LocalDate.now(),
                    currency = "$",
                    imagePath = "image.jpg"
                )
            )
        )
    }
}