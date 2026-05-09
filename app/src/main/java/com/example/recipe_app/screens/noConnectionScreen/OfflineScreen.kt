package com.example.recipe_app.screens.noConnectionScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe_app.R

@Composable
fun OfflineScreen(
    modifier: Modifier = Modifier,
    onTryAgain: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_connection_bro),
            contentDescription = "No Internet",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.you_are_offline),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Text(
            text = stringResource(R.string.check_your_connection_and_try_again),
            color = Color.Gray
        )
        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onTryAgain
        ) {
            Text(
                text = stringResource(R.string.try_again)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun OfflineScreenPreview() {
    OfflineScreen(onTryAgain = {})
}
