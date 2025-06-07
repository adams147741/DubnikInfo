package com.example.dubnikinfo.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dubnikinfo.DubnikInfoApp
import com.example.dubnikinfo.R
import com.example.dubnikinfo.data.DefaultAppContainer
import com.example.dubnikinfo.data.local.trash.TrashType
import com.example.dubnikinfo.presentation.ui.actualities.ActualitiesScreen
import com.example.dubnikinfo.presentation.ui.actualities.NewsViewModel
import com.example.dubnikinfo.presentation.ui.garbageCollection.GarbageCollectionScreen
import com.example.dubnikinfo.presentation.ui.garbageCollection.GarbageViewModel
import com.example.dubnikinfo.presentation.ui.home.HomeScreen
import com.example.dubnikinfo.presentation.ui.home.HomeViewModel
import com.example.dubnikinfo.presentation.ui.places.PlaceMapScreen
import com.example.dubnikinfo.presentation.ui.places.PlaceViewModel
import java.time.LocalDate

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext as DubnikInfoApp
    val appContainer = context.appContainer as DefaultAppContainer
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onCardClick = { },
                homeViewModel = HomeViewModel(appContainer.trashRepository, appContainer.weatherRepository)
            )
        }
        composable(Screen.Actualities.route) {
            ActualitiesScreen(
                viewModel = NewsViewModel(appContainer.newsRepository),
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Trash.route) {
            GarbageCollectionScreen(
                viewModel = GarbageViewModel(appContainer.trashRepository),
                onDayClick = { _, _ -> },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Places.route) {
            PlaceMapScreen(
                viewModel = PlaceViewModel(appContainer.placesRepository),
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}