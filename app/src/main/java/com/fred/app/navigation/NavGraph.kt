package com.fred.app.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fred.app.presentation.home.HomeScreen
import com.fred.app.presentation.profile.ProfileScreen
import com.fred.app.presentation.splash.SplashScreen
import com.fred.app.ui.component.DefaultScaffold
import com.fred.app.util.navigate
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(startDestination: String = NavDirections.Home.route) {
  val navController = rememberAnimatedNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  DefaultScaffold(
      bottomBar = {
          BottomNavigationBar(navController)
      },
  ) { innerPadding ->
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        Modifier.padding(innerPadding)) {
          composable(NavDirections.Splash.route) {
            SplashScreen(
                hiltViewModel(),
                navigateToHome = {
                  navController.navigate(
                      route = NavDirections.Home.route, popUpTo = NavDirections.Splash.route)
                },
                navigateToLogin = {
                  navController.navigate(
                      route = NavDirections.Login.route, popUpTo = NavDirections.Splash.route)
                })
          }


          composable(NavDirections.Home.route) {
            HomeScreen(
                hiltViewModel(),
                navigateToProfile = { navController.navigate(route = NavDirections.Profile.route) },
               )
          }

        composable (NavDirections.Leaderboard.route) { }

          composable(NavDirections.Profile.route) {
            ProfileScreen(hiltViewModel(), navigateToBack = { navController.popBackStack() })
          }
        }
  }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Leaderboard", Icons.Default.StarRate, "leaderboard")
    )

    NavigationBar {
        val currentRoute = currentRoute(navController)
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

// Helper to determine current route
@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)
