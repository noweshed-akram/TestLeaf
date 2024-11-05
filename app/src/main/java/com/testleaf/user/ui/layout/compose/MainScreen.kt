package com.testleaf.user.ui.layout.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.testleaf.user.navigation.AuthScreen
import com.testleaf.user.navigation.currentRoute
import com.testleaf.user.navigation.Graph
import com.testleaf.user.navigation.RootNavGraph
import com.testleaf.user.ui.component.AppBarWithArrow
import com.testleaf.user.utils.AppConstant.AUTH_TOKEN
import com.testleaf.user.viewmodel.AsesmntViewModel
import com.testleaf.user.viewmodel.AuthViewModel
import com.testleaf.user.viewmodel.EntityViewModel
import com.testleaf.user.viewmodel.QuesViewModel
import com.testleaf.user.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by noweshedakram on 24/6/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel,
    quesViewModel: QuesViewModel,
    entityViewModel: EntityViewModel
) {

    var isLoggedIn by rememberSaveable { (mutableStateOf(false)) }

    LaunchedEffect(key1 = true) {
        entityViewModel.getUserData().collectLatest { user ->
            if (user != null) {
                AUTH_TOKEN = user.accessToken
                isLoggedIn = true
            } else {
                isLoggedIn = false
            }
        }
    }

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

            val startDestination = if (isLoggedIn) {
                Graph.HOME
            } else {
                Graph.AUTHENTICATION
            }

            RootNavGraph(
                scrollBehavior = scrollBehavior,
                navController = navController,
                startDestination = startDestination,
                authViewModel = authViewModel,
                userViewModel = userViewModel,
                asesmntViewModel = asesmntViewModel,
                quesViewModel = quesViewModel,
                entityViewModel = entityViewModel
            )
        }
    }

}