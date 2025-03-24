package com.artemissoftware.hermesreceipts.core.presentation.composables.permissions

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun rememberPermissionLauncher(
    permission: String,
    onPermissionGranted: () -> Unit,
    onPermissionNotGranted: () -> Unit,
): () -> Unit {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted){
            onPermissionGranted()
        } else {
            onPermissionNotGranted()
        }
    }

    return { launcher.launch(permission) }
}
