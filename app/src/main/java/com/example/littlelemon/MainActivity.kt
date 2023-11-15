package com.example.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNavigation()
        }
    }

    private val sharedPref by lazy {
        getSharedPreferences("LittleLemon.prefs", MODE_PRIVATE)
    }

    private fun startDest(): String {
        var dest: String
        if (sharedPref.contains("firstName") && sharedPref.contains("lastName")
            && sharedPref.contains("email")) {
            dest = Home.route
        }
        else {
            dest = Onboarding.route
        }
        return dest
    }

    @Composable
    fun MyNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController,
            startDestination = startDest()
        ) {
            composable(Onboarding.route) {
                Onboarding(navController, sharedPref)
            }
            composable(Home.route) {
                Home(navController)
            }
            composable(Profile.route) {
                Profile(navController, sharedPref)
            }
        }
    }
}
