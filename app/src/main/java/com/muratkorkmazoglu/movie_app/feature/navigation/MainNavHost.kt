package com.muratkorkmazoglu.movie_app.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.muratkorkmazoglu.movie_app.feature.splash.splashRoute
import com.muratkorkmazoglu.movie_app.feature.splash.splashScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = splashRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        splashScreen {

        }
        /*  splashScreen(navigateToLogin = {
              navController.navigateLoginScreen(
                  NavOptions.Builder().setPopUpTo(0, true).build()
              )
          }, navigateToHome = {
              navController.navigateHomeScreen(
                  NavOptions.Builder().setPopUpTo(0, true).build()
              )
          }, navigateToLanding = {
              navController.navigateToLanding(
                  NavOptions.Builder().setPopUpTo(0, true).build()
              )
          })
          homeScreen(navigateToNotifications = {
              navController.navigateToNotification()
          })*/
    }
}