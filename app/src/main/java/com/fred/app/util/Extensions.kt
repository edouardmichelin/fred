package com.fred.app.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController

fun NavHostController.navigate(route: String, popUpTo: String) {
    this.navigate(route) {
        popUpTo(popUpTo) {
            inclusive = true
        }
    }
}

@Composable
fun NavigateTo(navigation: () -> Unit) {
    LaunchedEffect(Unit) {
        navigation()
    }
}