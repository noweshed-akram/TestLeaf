package com.awsprep.user.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.BuildCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.awsprep.user.ui.layout.compose.bottombar.AcronymsScreen
import com.awsprep.user.ui.layout.compose.bottombar.AssessmentScreen
import com.awsprep.user.ui.layout.compose.bottombar.DefinitionScreen
import com.awsprep.user.ui.layout.compose.bottombar.PractisesScreen
import com.awsprep.user.ui.layout.compose.bottombar.MyProfileScreen
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.UserViewModel

/**
 * Created by noweshedakram on 16/8/23.
 */
@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = BottomNavScreen.Assessment.route,
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        route = Graph.HOME
    ) {

        composable(BottomNavScreen.Assessment.route) {
            AssessmentScreen(
                navController = navController,
                asesmntViewModel = asesmntViewModel
            )
        }

        composable(BottomNavScreen.Practices.route) {
            PractisesScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable(BottomNavScreen.Acronyms.route) {
            AcronymsScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable(BottomNavScreen.Definition.route) {
            DefinitionScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable(BottomNavScreen.MyProfile.route) {
            MyProfileScreen(
                navController = navController, userViewModel = userViewModel
            )
        }

        ContentNavGraph(
            navController = navController,
            userViewModel = userViewModel,
            asesmntViewModel = asesmntViewModel
        )
    }
}