package com.artemissoftware.hermesreceipts.core.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.hermesreceipts.R
import com.artemissoftware.hermesreceipts.core.designsystem.composables.button.HRButton
import com.artemissoftware.hermesreceipts.core.designsystem.composables.scaffold.HRScaffold
import com.artemissoftware.hermesreceipts.core.designsystem.spacing
import com.artemissoftware.hermesreceipts.ui.theme.HermesReceiptsTheme

@Composable
fun ErrorScreen(
    errorMessage: String,
    onConfirm: () -> Unit
) {
    BackHandler {
        onConfirm()
    }

    HRScaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.spacing.spacing1_5),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.an_error_has_occurred),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.spacing2))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.spacing3))

                HRButton(
                    modifier = Modifier.fillMaxWidth(0.5F),
                    text = stringResource(R.string.close),
                    onClick = onConfirm
                )
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
private fun ErrorScreenPreview() {
    HermesReceiptsTheme {
        ErrorScreen(
            errorMessage = "An error occurred. Please try again.",
            onConfirm = {}
        )
    }
}