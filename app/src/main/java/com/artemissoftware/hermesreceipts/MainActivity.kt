package com.artemissoftware.hermesreceipts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.hermesreceipts.core.data.repository.PhotoRepositoryImpl
import com.artemissoftware.hermesreceipts.feature.capture.CameraViewModel
import com.artemissoftware.hermesreceipts.feature.capture.presentation.capture.CaptureScreen
import com.artemissoftware.hermesreceipts.feature.receipts.presentation.dashboard.DashboardScreen
import com.artemissoftware.hermesreceipts.navigation.RootNavGraph
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<CameraViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.lolo = PhotoRepositoryImpl(this)
        enableEdgeToEdge()
        setContent {
            HermesReceiptsTheme {
                val navController = rememberNavController()
                RootNavGraph(navController = navController)
            }
        }
    }
}
