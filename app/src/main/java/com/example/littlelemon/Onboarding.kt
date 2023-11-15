package com.example.littlelemon

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Onboarding(navController: NavHostController, sharedPreferences: SharedPreferences) {

    val context = LocalContext.current

    var firstName = remember {
        mutableStateOf("")
    }
    var lastName = remember {
        mutableStateOf("")
    }
    var email = remember {
        mutableStateOf("")
    }

    Column {
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
                .align(Alignment.CenterHorizontally))
        Surface(
            color = Color(0xFF333333),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Let's get to know you",
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(50.dp)
                    .wrapContentWidth(),
                color = Color(0xFFFFFFFF),
            )
        }
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Personal Information",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(vertical = 30.dp),
                fontSize = 18.sp,
                color = Color(0xFF333333)
            )
            OutlinedTextField(value = firstName.value,
                onValueChange = { firstName.value = it },
                label = {Text("First Name")},
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp))
            OutlinedTextField(value = lastName.value,
                onValueChange = { lastName.value = it },
                label = {Text("Last Name")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp))
            OutlinedTextField(value = email.value,
                onValueChange = { email.value = it},
                label = {Text("Email")},
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    if (firstName.value.isEmpty() || lastName.value.isEmpty()
                        || email.value.isEmpty()) {
                        Toast.makeText(context, "Registration unsuccessful!. Please enter all data",
                            Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(context,"Registration Successful!", Toast.LENGTH_SHORT).show()
                        sharedPreferences.edit()
                            .putString("firstName", firstName.value)
                            .putString("lastName", lastName.value)
                            .putString("email", email.value)
                            .apply()
                        navController.navigate(Home.route)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4CE14)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Register",
                    color = Color.Black
                )
            }
        }
    }

    
    
}