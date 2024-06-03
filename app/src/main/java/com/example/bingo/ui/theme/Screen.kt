package com.example.bingo.ui.theme

sealed class Screen (val route: String) {
    object MainScreen: Screen("main_screen")
    object DetailScreen: Screen("detail_screen")
    object WinningScreen: Screen("winning_screen")
}