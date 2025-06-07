package com.example.dubnikinfo.presentation.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Actualities : Screen("actualities")
    object Trash : Screen("trash")
    object Places : Screen("places")
    object Reports : Screen("reports")
}