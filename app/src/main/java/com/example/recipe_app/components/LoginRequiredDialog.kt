package com.example.recipe_app.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipe_app.R
import com.example.recipe_app.ui.theme.OrangeVariant

@Composable
fun LoginRequiredDialog(
    onDismiss: () -> Unit,
    onLoginConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.account_required), fontWeight = FontWeight.Bold)
        },
        text = {
            Text(stringResource(R.string.you_need_to_login))
        },
        confirmButton = {
            Button(
                onClick = onLoginConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangeVariant // Matches your app's theme!
                )
            ) {
                Text("Log In")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.not_now), color = Color.Gray)
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}