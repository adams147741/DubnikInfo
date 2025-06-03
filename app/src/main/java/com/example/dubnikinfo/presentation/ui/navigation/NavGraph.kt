package com.example.dubnikinfo.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dubnikinfo.R
import com.example.dubnikinfo.data.NewsRepositoryImpl
import com.example.dubnikinfo.data.RssDataSource
import com.example.dubnikinfo.data.TrashType
import com.example.dubnikinfo.presentation.ui.actualities.ActualitiesScreen
import com.example.dubnikinfo.presentation.ui.actualities.NewsViewModel
import com.example.dubnikinfo.presentation.ui.garbageCollection.GarbageCollectionScreen
import com.example.dubnikinfo.presentation.ui.garbageCollection.GarbageCollectionScreenPreview
import com.example.dubnikinfo.presentation.ui.home.HomeScreen
import java.time.LocalDate
import java.time.YearMonth

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
                navController = navController,
                onCardClick = { }
            )
        }
        composable(Screen.Actualities.route) {
            // Create or retrieve the ViewModel
            val viewModel = remember {
                NewsViewModel(
                    NewsRepositoryImpl(RssDataSource())
                )
            }

            // Observe the state
            val news by viewModel.news.collectAsState()

            // Filter only "Úradná tabuľa" items
            //val officialBoardNews = news.filter { it.category == "Úradná tabuľa" }

            // Pass the filtered list to the screen
            ActualitiesScreen(
                news = news,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Trash.route) {
            GarbageCollectionScreen(
                startMonth = YearMonth.of(2025, 5),
                endMonth = YearMonth.of(2025, 5),
                events = mapOf(
                    LocalDate.of(2025, 5, 6) to listOf(TrashType.MUNICIPAL),
                    LocalDate.of(2025, 5, 9) to listOf(TrashType.MUNICIPAL)
                ),
                onDayClick = { _, _ -> },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}