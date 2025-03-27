package com.artemissoftware.hermesreceipts.feature.receipts.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
internal fun DetailScreen(
    navigateToError: (String) -> Unit,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle().value

    DetailContent(
        state = state,
        onEvent = viewModel::onTriggerEvent,
        navigateBack = navigateBack
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onNavigate = {
            when(it.route){
                ReceiptsRoute.ERROR -> navigateToError((it.value as UiText).asString(context))
                else -> Unit
            }
        },
    )
}

@Composable
private fun DetailContent(
    navigateBack: () -> Unit,
    onEvent: (DetailEvent) -> Unit,
    state: DetailState
) {
    var request = ImageRequest
        .Builder(LocalContext.current)
        .placeholder(R.drawable.ic_placeholder)
        .crossfade(true)

    state.receipt?.imagePath?.let {
        request = request
            .data(it)
            .error(R.drawable.ic_broken)
    }

    HRScaffold(
        isLoading = state.isLoading,
        topBar = {
            HRTopBar(
                title = stringResource(R.string.receipt_details),
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

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.spacing4))

                state.receipt?.let{
                    ReceiptInfo(
                        receipt = it,
                        modifier = Modifier.fillMaxWidth(0.5F)
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.spacing4))

                    HRButton(
                        text = stringResource(R.string.delete),
                        imageVector = Icons.Default.Delete,
                        onClick = {
                            onEvent(DetailEvent.Delete)
                            navigateBack()
                        },
                        modifier = Modifier.fillMaxWidth(0.8F)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailContentPreview() {
    HermesReceiptsTheme {
        DetailContent(
            navigateBack = {},
            onEvent = {},
            state = DetailState(
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