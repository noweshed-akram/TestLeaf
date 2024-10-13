package com.testleaf.user.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testleaf.user.viewmodel.AuthViewModel
import com.testleaf.user.viewmodel.UserViewModel
import com.testleaf.user.ui.layout.compose.HomeScreen
import com.testleaf.user.viewmodel.ApiViewModel
import com.testleaf.user.viewmodel.AsesmntViewModel
import com.testleaf.user.viewmodel.EntityViewModel
import com.testleaf.user.viewmodel.QuesViewModel

/**
 * Created by noweshedakram on 5/8/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavGraph(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController = rememberNavController(),
    route: String = Graph.ROOT,
    startDestination: String = Graph.AUTHENTICATION,
    authViewModel: AuthViewModel,
    apiViewModel: ApiViewModel,
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel,
    quesViewModel: QuesViewModel,
    entityViewModel: EntityViewModel
) {
    NavHost(
        navController = navController,
        route = route,
        startDestination = startDestination
    ) {

        AuthNavGraph(
            navController = navController,
            authViewModel = authViewModel,
            apiViewModel = apiViewModel
        )

        composable(Graph.HOME) {
            HomeScreen(
                scrollBehavior = scrollBehavior,
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