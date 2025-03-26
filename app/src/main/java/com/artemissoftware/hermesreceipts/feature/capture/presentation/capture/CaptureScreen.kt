package com.artemissoftware.hermesreceipts.feature.capture.presentation.capture

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.hermesreceipts.core.designsystem.composables.button.HRCircularIconButton
import com.artemissoftware.hermesreceipts.core.designsystem.composables.scaffold.HRScaffold
import com.artemissoftware.hermesreceipts.core.designsystem.spacing
import com.artemissoftware.hermesreceipts.core.presentation.composables.events.ManageUIEvents
import com.artemissoftware.hermesreceipts.core.presentation.util.extensions.capturePhoto
import com.artemissoftware.hermesreceipts.feature.capture.presentation.composables.CameraPreview
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme

@Composable
fun CaptureScreen(
    navigateToValidation:(String) -> Unit,
    viewModel: CaptureViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState().value

    CaptureContent(
        state = state,
        onEvent = viewModel::onTriggerEvent
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onNavigate = {
            when(it.route){
                "validate" -> navigateToValidation(it.value as String)
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
                state.capturedBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )
                } ?: run {
                    CameraPreview(
                        controller = controller,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    HRCircularIconButton(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(MaterialTheme.spacing.spacing2),
                        icon = Icons.Default.PhotoCamera,
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