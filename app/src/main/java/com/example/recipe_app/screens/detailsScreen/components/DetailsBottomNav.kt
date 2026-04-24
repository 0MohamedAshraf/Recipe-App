package com.example.recipe_app.screens.detailsScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipe_app.R
import com.example.recipe_app.ui.theme.OrangeVariant

@Composable
fun DetailsBottomNav(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Button(
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(containerColor = OrangeVariant),
        modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.fork_knife),
            contentDescription = "Fork Knife Icon",
            modifier = Modifier.size(15.dp,20.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Add To Planner"
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DetailsBottomNavPreview(){

    DetailsBottomNav({})


}