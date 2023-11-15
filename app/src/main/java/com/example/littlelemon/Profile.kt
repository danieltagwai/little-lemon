package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Profile(navController:NavHostController ,sharedPreferences: SharedPreferences) {

    val firstName = sharedPreferences.getString("firstName", "").toString()
    val lastName = sharedPreferences.getString("lastName", "").toString()
    val email = sharedPreferences.getString("email", "").toString()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .width(200.dp)
                .height(100.dp))
        Text(
            text = "Profile Information:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
                .padding(top = 50.dp, bottom = 20.dp),
            color = Color(0xFF333333)
        )
        OutlinedTextField(value = firstName,
            onValueChange = { },
            enabled = false,
            label = { Text(text = "First Name")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp))
        OutlinedTextField(value = lastName,
            onValueChange = { },
            enabled = false,
            label = { Text(text = "Last Name")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp))
        OutlinedTextField(value = email,
            onValueChange = { },
            enabled = false,
            label = { Text(text = "Email")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp))
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                sharedPreferences.edit()
                    .clear()
                    .apply()
                navController.navigate(Onboarding.route)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4CE14)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Log out",
                color = Color.Black)
        }
    }
}