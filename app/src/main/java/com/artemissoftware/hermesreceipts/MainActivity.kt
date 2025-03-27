package com.artemissoftware.hermesreceipts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.hermesreceipts.navigation.RootNavGraph
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HermesReceiptsTheme {
                val navController = rememberNavController()
                RootNavGraph(navController = navController)
            }
        }
    }
}
