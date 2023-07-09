package com.hilt.mvvm.ui.appnavigation

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Details : Screen("details")
}