package com.fred.app.navigation

sealed class NavDirections(val route: String) {
  object Splash : NavDirections("splash")
  object Login : NavDirections("login")
  object Home : NavDirections("home")
  object Profile : NavDirections("profile")
  object Personal : NavDirections("personal")
  object Leaderboard : NavDirections("leaderboard")
  object Transports : NavDirections("transports")
}
