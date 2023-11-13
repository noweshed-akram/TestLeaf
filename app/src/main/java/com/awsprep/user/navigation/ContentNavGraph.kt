package com.awsprep.user.navigation

import androidx.core.os.BuildCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.awsprep.user.ui.layout.compose.ChapterScreen
import com.awsprep.user.ui.layout.compose.EditProfileScreen
import com.awsprep.user.ui.layout.compose.NotificationScreen
import com.awsprep.user.ui.layout.compose.QuestionScreen
import com.awsprep.user.ui.layout.compose.SectionScreen
import com.awsprep.user.ui.layout.compose.TimerScreen
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.UserViewModel

/**
 * Created by noweshedakram on 16/8/23.
 */
@OptIn(BuildCompat.PrereleaseSdkCheck::class)
fun NavGraphBuilder.ContentNavGraph(
    navController: NavHostController,
    route: String = Graph.CONTENT,
    startDestination: String = ContentNavScreen.EditProfile.route,
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel,
    quesViewModel: QuesViewModel
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

        composable(ContentNavScreen.Chapters.route) {
            ChapterScreen(navController = navController, asesmntViewModel = asesmntViewModel)
        }

        composable(ContentNavScreen.Sections.route) {
            SectionScreen(navController = navController, asesmntViewModel = asesmntViewModel)
        }

        composable(ContentNavScreen.Timer.route) {
            TimerScreen(navController = navController)
        }

        composable(ContentNavScreen.Question.route) {
            QuestionScreen(onSubmitAnswers = {
                navController.navigate(ContentNavScreen.Result.route)
            }, quesViewModel = quesViewModel)
        }

        composable(ContentNavScreen.Result.route) {

        }

    }

}