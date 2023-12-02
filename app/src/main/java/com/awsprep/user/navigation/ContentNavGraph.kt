package com.awsprep.user.navigation

import androidx.core.os.BuildCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.awsprep.user.ui.layout.compose.AllCourseScreen
import com.awsprep.user.ui.layout.compose.ChapterScreen
import com.awsprep.user.ui.layout.compose.EditProfileScreen
import com.awsprep.user.ui.layout.compose.NotificationScreen
import com.awsprep.user.ui.layout.compose.PracticeSetsScreen
import com.awsprep.user.ui.layout.compose.RandomSetsScreen
import com.awsprep.user.ui.layout.compose.ResultDashboard
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

        composable(
            ContentNavScreen.Chapters.route.plus(ContentNavScreen.Chapters.objectPath),
            arguments = listOf(
                navArgument(ContentNavScreen.Chapters.objectName) {
                    type = NavType.StringType
                }
            )
        ) {
            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            response?.let {
                ChapterScreen(
                    navController = navController,
                    asesmntViewModel = asesmntViewModel,
                    courseId = it
                )
            }
        }

        composable(
            ContentNavScreen.Sections.route
                .plus(ContentNavScreen.Sections.objectPath)
                .plus(ContentNavScreen.Sections.objectPathTwo),
            arguments = listOf(
                navArgument(ContentNavScreen.Sections.objectName) {
                    type = NavType.StringType
                },
                navArgument(ContentNavScreen.Sections.objectNameTwo) {
                    type = NavType.StringType
                }
            )
        ) {
            val course = it.arguments?.getString(ContentNavScreen.Sections.objectName)
            val chapter = it.arguments?.getString(ContentNavScreen.Sections.objectNameTwo)

            course?.let { courseId ->
                chapter?.let { chapterId ->
                    SectionScreen(
                        navController = navController, asesmntViewModel = asesmntViewModel,
                        courseId = courseId, chapterId = chapterId
                    )
                }
            }

        }

        composable(
            ContentNavScreen.Timer.route
                .plus(ContentNavScreen.Timer.objectPath)
                .plus(ContentNavScreen.Timer.objectPathTwo)
                .plus(ContentNavScreen.Timer.objectPathThree),
            arguments = listOf(
                navArgument(ContentNavScreen.Timer.objectName) {
                    type = NavType.StringType
                },
                navArgument(ContentNavScreen.Timer.objectNameTwo) {
                    type = NavType.StringType
                },
                navArgument(ContentNavScreen.Timer.objectNameThree) {
                    type = NavType.StringType
                }
            )
        ) {
            val course = it.arguments?.getString(ContentNavScreen.Timer.objectName)
            val chapter = it.arguments?.getString(ContentNavScreen.Timer.objectNameTwo)
            val section = it.arguments?.getString(ContentNavScreen.Timer.objectNameThree)

            course?.let { courseId ->
                chapter?.let { chapterId ->
                    section?.let { sectionId ->
                        TimerScreen(
                            navController = navController,
                            quesViewModel = quesViewModel,
                            courseId = courseId,
                            chapterId = chapterId,
                            sectionId = sectionId
                        )
                    }
                }
            }

        }

        composable(ContentNavScreen.ReviewQues.route) {
            ReviewQuesScreen(navController = navController, quesViewModel = quesViewModel)
        }

        composable(ContentNavScreen.RandomSets.route) {
            RandomSetsScreen()
        }

        composable(ContentNavScreen.PracticeSets.route) {
            PracticeSetsScreen()
        }

        composable(
            ContentNavScreen.Test.route.plus(ContentNavScreen.Test.objectPath),
            arguments = listOf(
                navArgument(ContentNavScreen.Test.objectName) {
                    type = NavType.BoolType
                }
            )
        ) {

            val activeTimer = it.arguments?.getBoolean(ContentNavScreen.Test.objectName, true)

            activeTimer?.let {
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
                    },
                    activeTimer = it,
                    quesViewModel = quesViewModel
                )
            }
        }

        composable(ContentNavScreen.Result.route) {
            ResultScreen(userViewModel = userViewModel)
        }

        composable(ContentNavScreen.ResultDashboard.route) {
            ResultDashboard(navController = navController, userViewModel = userViewModel)
        }

    }

}