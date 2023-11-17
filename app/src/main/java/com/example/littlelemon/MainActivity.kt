package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNavigation()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                val result = fetchMenu()
                saveMenuToDatabase(result)
            }
        }
    }

    private val sharedPref by lazy {
        getSharedPreferences("LittleLemon.prefs", MODE_PRIVATE)
    }

    private fun startDest(): String {
        val dest: String
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
                Home(navController, database)
            }
            composable(Profile.route) {
                Profile(navController, sharedPref)
            }
        }
    }

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val response = client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json").body<MenuNetwork>()
        return response.menu
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom()}
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }



}
