package com.awsprep.user.ui.layout.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.awsprep.user.navigation.AuthScreen
import com.awsprep.user.navigation.currentRoute
import com.awsprep.user.navigation.Graph
import com.awsprep.user.navigation.RootNavGraph
import com.awsprep.user.ui.component.AppBarWithArrow
import com.awsprep.user.viewmodel.ApiViewModel
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.AuthViewModel
import com.awsprep.user.viewmodel.EntityViewModel
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.UserViewModel

/**
 * Created by noweshedakram on 24/6/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    apiViewModel: ApiViewModel,
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel,
    quesViewModel: QuesViewModel,
    entityViewModel: EntityViewModel
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentRoute(navController = navController) == AuthScreen.EmailRegistration.route) {
                AppBarWithArrow(
                    scrollBehavior = scrollBehavior,
                    title = "Sign Up",
                    titleColor = Color.Black,
                    topBarColor = Color.White,
                    backBtnColor = Color.Black
                ) {
                    navController.popBackStack()
                }
            } else if (currentRoute(navController = navController) == AuthScreen.ForgotPassword.route) {
                AppBarWithArrow(
                    scrollBehavior = scrollBehavior,
                    title = "Forgot Password",
                    titleColor = Color.Black,
                    topBarColor = Color.White,
                    backBtnColor = Color.Black
                ) {
                    navController.popBackStack()
                }
            }
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(top = it.calculateBottomPadding() + it.calculateTopPadding())
        ) {

            val startDestination = if (userViewModel.isUserAuthenticated) {
                Graph.HOME
            } else {
                Graph.AUTHENTICATION
            }

            RootNavGraph(
                scrollBehavior = scrollBehavior,
                navController = navController,
                startDestination = startDestination,
                authViewModel = authViewModel,
                apiViewModel = apiViewModel,
                userViewModel = userViewModel,
                asesmntViewModel = asesmntViewModel,
                quesViewModel = quesViewModel,
                entityViewModel = entityViewModel
            )
        }
    }

}