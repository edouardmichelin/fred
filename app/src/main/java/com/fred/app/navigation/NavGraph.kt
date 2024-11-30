package com.fred.app.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.fred.app.presentation.create_chat.CreateChatScreen
import com.fred.app.presentation.home.HomeScreen
import com.fred.app.presentation.login.LoginScreen
import com.fred.app.presentation.profile.ProfileScreen
import com.fred.app.presentation.register.RegisterScreen
import com.fred.app.presentation.splash.SplashScreen
import com.fred.app.ui.component.DefaultScaffold
import com.fred.app.util.navigate

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(startDestination: String = NavDirections.Splash.route) {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    DefaultScaffold(
        bottomBar = {
        },
    ) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination,
            Modifier.padding(innerPadding)
        ) {
            composable(NavDirections.Splash.route) {
                SplashScreen(
                    hiltViewModel(),
                    navigateToHome = {
                        navController.navigate(
                            route = NavDirections.Home.route,
                            popUpTo = NavDirections.Splash.route
                        )
                    },
                    navigateToLogin = {
                        navController.navigate(
                            route = NavDirections.Login.route,
                            popUpTo = NavDirections.Splash.route
                        )
                    }
                )
            }
            composable(NavDirections.Login.route) {
                LoginScreen(
                    hiltViewModel(),
                    navigateToRegister = {
                        navController.navigate(
                            route = NavDirections.Register.route,
                            popUpTo = NavDirections.Register.route
                        )
                    },
                    navigateToHome = {
                        navController.navigate(
                            route = NavDirections.Home.route,
                            popUpTo = NavDirections.Register.route
                        )
                    }
                )
            }
            composable(NavDirections.Register.route) {
                RegisterScreen(
                    hiltViewModel(),
                    navigateToBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(NavDirections.Home.route) {
                HomeScreen(
                    hiltViewModel(),
                    navigateToProfile = {
                        navController.navigate(
                            route = NavDirections.Profile.route
                        )
                    },
                    navigateToCreateChat = {
                        navController.navigate(
                            route = NavDirections.CreateChat.route
                        )
                    }
                )
            }
            composable(NavDirections.Profile.route) {
                ProfileScreen(
                    hiltViewModel(),
                    navigateToBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(NavDirections.CreateChat.route) {
                CreateChatScreen(
                    hiltViewModel(),
                    navigateToBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}