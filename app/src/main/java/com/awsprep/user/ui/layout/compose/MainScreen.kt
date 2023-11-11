package com.awsprep.user.ui.layout.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.awsprep.user.navigation.AuthScreen
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.navigation.CurrentRoute
import com.awsprep.user.navigation.Graph
import com.awsprep.user.navigation.RootNavGraph
import com.awsprep.user.ui.component.AppBarWithArrow
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.AuthViewModel
import com.awsprep.user.viewmodel.UserViewModel

/**
 * Created by noweshedakram on 24/6/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (CurrentRoute(navController = navController) == AuthScreen.ForgotPassword.route) {
                AppBarWithArrow(title = "Password Recover") {
                    navController.popBackStack()
                }
            }
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(top = it.calculateBottomPadding() + it.calculateTopPadding())
        ) {

            var startDestination = ""

            startDestination = if (userViewModel.isUserAuthenticated) {
                Graph.HOME
            } else {
                Graph.AUTHENTICATION
            }

            RootNavGraph(
                navController = navController,
                startDestination = startDestination,
                authViewModel = authViewModel,
                userViewModel = userViewModel,
                asesmntViewModel = asesmntViewModel
            )
        }
    }

}