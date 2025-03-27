package com.artemissoftware.hermesreceipts.feature.capture.presentation.capture

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artemissoftware.hermesreceipts.core.designsystem.composables.button.HRCircularIconButton
import com.artemissoftware.hermesreceipts.core.designsystem.composables.scaffold.HRScaffold
import com.artemissoftware.hermesreceipts.core.designsystem.dimension
import com.artemissoftware.hermesreceipts.core.designsystem.spacing
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.ManageUIEvents
import com.artemissoftware.hermesreceipts.core.presentation.util.UiText
import com.artemissoftware.hermesreceipts.core.presentation.util.extensions.capturePhoto
import com.artemissoftware.hermesreceipts.feature.capture.presentation.composables.CameraPreview
import com.artemissoftware.hermesreceipts.feature.capture.presentation.navigation.CaptureRoute
import com.artemissoftware.hermesreceipts.ui.theme.Blue10
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme

@Composable
internal fun CaptureScreen(
    navigateToError:(String) -> Unit,
    navigateToValidation:(String) -> Unit,
    viewModel: CaptureViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle().value

    CaptureContent(
        state = state,
        onEvent = viewModel::onTriggerEvent
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onNavigate = {
            when(it.route){
                CaptureRoute.VALIDATION -> navigateToValidation(it.value as String)
                CaptureRoute.ERROR -> navigateToError((it.value as UiText).asString(context))
                else -> Unit
            }
        },
    )
}

@Composable
private fun CaptureContent(
    state: CaptureState,
    onEvent: (CaptureEvent) -> Unit
) {

    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    HRScaffold(
        isLoading = state.isLoading,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CameraPreview(
                    controller = controller,
                    modifier = Modifier
                        .fillMaxSize()
                )

                HRCircularIconButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(MaterialTheme.spacing.spacing3),
                    icon = Icons.Default.PhotoCamera,
                    size = MaterialTheme.dimension.iconSizeBig,
                    backgroundColor = Blue10,
                    onClick = {
                        context.capturePhoto(
                            controller = controller,
                            onPhotoCaptured = { bitmap ->
                                onEvent(CaptureEvent.UpdateCapturedPhoto(bitmap))
                            },
                            onError = {

                            }
                        )
                    },
                    contentColor = Color.White
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CaptureScreenPreview() {
    HermesReceiptsTheme {
        CaptureContent(
            state = CaptureState(),
            onEvent = {}
        )
    }
}