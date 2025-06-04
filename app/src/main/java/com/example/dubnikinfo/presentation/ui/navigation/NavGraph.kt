package com.example.dubnikinfo.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dubnikinfo.R
import com.example.dubnikinfo.data.DefaultAppContainer
import com.example.dubnikinfo.data.local.thrash.TrashType
import com.example.dubnikinfo.presentation.ui.actualities.ActualitiesScreen
import com.example.dubnikinfo.presentation.ui.actualities.NewsViewModel
import com.example.dubnikinfo.presentation.ui.garbageCollection.GarbageCollectionScreen
import com.example.dubnikinfo.presentation.ui.home.HomeScreen
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val appContainer = remember { DefaultAppContainer(context) }
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
                navController = navController,
                onCardClick = { }
            )
        }
        composable(Screen.Actualities.route) {
            val viewModel = remember {
                NewsViewModel(
                    appContainer.newsRepository
                )
            }

            val news by viewModel.news.collectAsState()

            ActualitiesScreen(
                news = news,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Trash.route) {
            GarbageCollectionScreen(
                events = mapOf(
                    LocalDate.of(2025, 6, 6) to listOf(TrashType.MUNICIPAL),
                    LocalDate.of(2025, 6, 10) to listOf(TrashType.MUNICIPAL, TrashType.GLASS),
                ),
                onDayClick = { _, _ -> },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}