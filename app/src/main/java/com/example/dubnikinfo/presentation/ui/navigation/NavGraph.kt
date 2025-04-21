package com.example.dubnikinfo.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dubnikinfo.R
import com.example.dubnikinfo.presentation.ui.actualities.ActualitiesScreen
import com.example.dubnikinfo.presentation.ui.home.HomeScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                temperature       = 23,
                weatherDescription= "123",
                trashIcon         = R.drawable.zbertko,
                trashDate         = "24.4.2024",
                onCardClick = {
                    navController.navigate(Screen.Actualities.route)
                }
            )
        }
        composable(Screen.Actualities.route) {
            ActualitiesScreen(
                news = listOf(),
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}