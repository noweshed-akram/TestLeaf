package com.awsprep.user.navigation

import androidx.core.os.BuildCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.awsprep.user.ui.layout.compose.EditProfileScreen
import com.awsprep.user.ui.layout.compose.NotificationScreen
import com.awsprep.user.viewmodel.UserViewModel

/**
 * Created by noweshedakram on 16/8/23.
 */
@OptIn(BuildCompat.PrereleaseSdkCheck::class)
fun NavGraphBuilder.ContentNavGraph(
    navController: NavHostController,
    route: String = Graph.CONTENT,
    startDestination: String = ContentNavScreen.EditProfile.route,
    userViewModel: UserViewModel
) {

    navigation(
        route = route, startDestination = startDestination
    ) {

        composable(ContentNavScreen.EditProfile.route) {
            EditProfileScreen(navController = navController, userViewModel = userViewModel)
        }

        composable(ContentNavScreen.Notification.route) {
            NotificationScreen(navController = navController)
        }
    }

}