package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun Home(navController: NavHostController) {
    Row(
        modifier = Modifier.padding(20.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Logo",
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    navController.navigate(Profile.route)
                }
        )
    }

}
