@file:OptIn(ExperimentalMaterial3Api::class)

package com.artemissoftware.hermesreceipts.core.designsystem.composables.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme

@Composable
fun HRScaffold(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    floatingActionButton: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Scaffold(
                modifier = Modifier
                    .then(modifier),
                floatingActionButton = floatingActionButton,
                topBar = topBar,
                content = { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            content.invoke()
                        }
                    }
                },
            )
        }

        if(isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun HRScaffoldPreview() {
    HermesReceiptsTheme {

        HRScaffold(
            isLoading = false,
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Scaffolding")
                }
            },
        )
    }
}