package com.awsprep.user.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.awsprep.user.viewmodel.AuthViewModel
import com.awsprep.user.viewmodel.UserViewModel
import com.awsprep.user.ui.layout.compose.HomeScreen
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.QuesViewModel

/**
 * Created by noweshedakram on 5/8/23.
 */
@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController(),
    route: String = Graph.ROOT,
    startDestination: String = Graph.AUTHENTICATION,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel,
    quesViewModel: QuesViewModel
) {
    NavHost(
        navController = navController,
        route = route,
        startDestination = startDestination
    ) {

        AuthNavGraph(
            navController = navController,
            authViewModel = authViewModel,
            userViewModel = userViewModel
        )

        composable(Graph.HOME) {
            HomeScreen(
                userViewModel = userViewModel,
                asesmntViewModel = asesmntViewModel,
                quesViewModel = quesViewModel
            )
        }
    }
}