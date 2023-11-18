package com.awsprep.user.navigation

import androidx.core.os.BuildCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.awsprep.user.ui.layout.compose.AllCourseScreen
import com.awsprep.user.ui.layout.compose.ChapterScreen
import com.awsprep.user.ui.layout.compose.EditProfileScreen
import com.awsprep.user.ui.layout.compose.NotificationScreen
import com.awsprep.user.ui.layout.compose.PracticeSetsScreen
import com.awsprep.user.ui.layout.compose.RandomSetsScreen
import com.awsprep.user.ui.layout.compose.ResultScreen
import com.awsprep.user.ui.layout.compose.ReviewQuesScreen
import com.awsprep.user.ui.layout.compose.TestScreen
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

        composable(ContentNavScreen.AllCourse.route) {
            AllCourseScreen(navController = navController, asesmntViewModel = asesmntViewModel)
        }

        composable(ContentNavScreen.Chapters.route) {
            ChapterScreen(navController = navController, asesmntViewModel = asesmntViewModel)
        }

        composable(ContentNavScreen.Sections.route) {
            SectionScreen(navController = navController, asesmntViewModel = asesmntViewModel)
        }

        composable(ContentNavScreen.ReviewQues.route) {
            ReviewQuesScreen(navController = navController)
        }

        composable(ContentNavScreen.RandomSets.route) {
            RandomSetsScreen()
        }

        composable(ContentNavScreen.PracticeSets.route) {
            PracticeSetsScreen()
        }

        composable(ContentNavScreen.Timer.route) {
            TimerScreen(navController = navController)
        }

        composable(ContentNavScreen.Test.route) {
            TestScreen(
                onBackPressed = {
                    navController.navigate(Graph.HOME) {
                        popUpTo(ContentNavScreen.Test.route)
                    }
                },
                onSubmitAnswers = {
                    navController.navigate(ContentNavScreen.Result.route) {
                        popUpTo(ContentNavScreen.Test.route)
                    }
                }, quesViewModel = quesViewModel
            )
        }

        composable(ContentNavScreen.Result.route) {
            ResultScreen(userViewModel = userViewModel)
        }

    }

}