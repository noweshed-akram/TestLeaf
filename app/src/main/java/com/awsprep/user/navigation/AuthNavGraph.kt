package com.awsprep.user.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.awsprep.user.ui.layout.compose.auth.CheckEmailScreen
import com.awsprep.user.ui.layout.compose.auth.EmailRegisterScreen
import com.awsprep.user.ui.layout.compose.auth.EmailSignScreen
import com.awsprep.user.ui.layout.compose.auth.ForgotPasswordScreen
import com.awsprep.user.viewmodel.AuthViewModel

/**
 * Created by noweshedakram on 23/6/23.
 */
fun NavGraphBuilder.AuthNavGraph(
    navController: NavHostController,
    route: String = Graph.AUTHENTICATION,
    startDestination: String = AuthScreen.EmailSignIn.route,
    authViewModel: AuthViewModel
) {
    navigation(
        route = route,
        startDestination = startDestination
    ) {

        composable(route = AuthScreen.EmailSignIn.route) {
            EmailSignScreen(
                navController = navController, authViewModel = authViewModel
            )
        }

        composable(route = AuthScreen.ForgotPassword.route) {
            ForgotPasswordScreen(
                navController = navController, authViewModel = authViewModel
            )
        }

        composable(route = AuthScreen.CheckEmail.route) {
            CheckEmailScreen(
                navController = navController
            )
        }

        composable(route = AuthScreen.EmailRegistration.route) {
            EmailRegisterScreen(
                navController = navController, authViewModel = authViewModel
            )
        }

    }
}