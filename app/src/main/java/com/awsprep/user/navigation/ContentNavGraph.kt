package com.awsprep.user.navigation

import androidx.core.os.BuildCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.awsprep.user.domain.models.ExamMetaData
import com.awsprep.user.domain.models.TestResult
import com.awsprep.user.ui.layout.compose.AllCourseScreen
import com.awsprep.user.ui.layout.compose.AnswerSheetScreen
import com.awsprep.user.ui.layout.compose.ChapterScreen
import com.awsprep.user.ui.layout.compose.EditProfileScreen
import com.awsprep.user.ui.layout.compose.NotificationScreen
import com.awsprep.user.ui.layout.compose.SubSetsScreen
import com.awsprep.user.ui.layout.compose.ResultDashboard
import com.awsprep.user.ui.layout.compose.ResultScreen
import com.awsprep.user.ui.layout.compose.ReviewQuesScreen
import com.awsprep.user.ui.layout.compose.SectionScreen
import com.awsprep.user.ui.layout.compose.TestScreen
import com.awsprep.user.ui.layout.compose.TimerScreen
import com.awsprep.user.utils.fromPrettyJson
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.EntityViewModel
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.UserViewModel
import com.google.gson.Gson

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
    quesViewModel: QuesViewModel,
    entityViewModel: EntityViewModel
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
            route = ContentNavScreen.Chapters.route.plus(ContentNavScreen.Chapters.objectPath),
            arguments = listOf(
                navArgument(ContentNavScreen.Chapters.objectName) {
                    type = NavType.StringType
                }
            ),
        ) {

            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            examMetaData?.let { data ->
                ChapterScreen(
                    navController = navController,
                    asesmntViewModel = asesmntViewModel,
                    examMetaData = data
                )
            }
        }

        composable(
            route = ContentNavScreen.Sections.route
                .plus(ContentNavScreen.Sections.objectPath),
            arguments = listOf(
                navArgument(ContentNavScreen.Sections.objectName) {
                    type = NavType.StringType
                }
            )
        ) {

            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            examMetaData?.let { data ->
                SectionScreen(
                    navController = navController,
                    asesmntViewModel = asesmntViewModel,
                    examMetaData = data
                )
            }

        }

        composable(
            route = ContentNavScreen.Timer.route
                .plus(ContentNavScreen.Timer.objectPath),
            arguments = listOf(
                navArgument(ContentNavScreen.Timer.objectName) {
                    type = NavType.StringType
                }
            )
        ) {

            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            examMetaData?.let { data ->
                TimerScreen(
                    navController = navController,
                    quesViewModel = quesViewModel,
                    entityViewModel = entityViewModel,
                    examMetaData = data
                )
            }
        }

        composable(route = ContentNavScreen.ReviewQues.route) {
            ReviewQuesScreen(
                navController = navController,
                quesViewModel = quesViewModel,
                entityViewModel = entityViewModel
            )
        }

        composable(
            route = ContentNavScreen.SubSets.route.plus(ContentNavScreen.SubSets.objectPath),
            arguments = listOf(
                navArgument(ContentNavScreen.SubSets.objectName) {
                    type = NavType.StringType
                }
            )
        ) {
            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            examMetaData?.let { data ->
                SubSetsScreen(
                    navController = navController,
                    asesmntViewModel = asesmntViewModel,
                    examMetaData = data
                )
            }

        }

        composable(
            route = ContentNavScreen.Test.route.plus(ContentNavScreen.Test.objectPath),
            arguments = listOf(
                navArgument(ContentNavScreen.Test.objectName) {
                    type = NavType.StringType
                }
            )
        ) {

            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            examMetaData?.let { data ->
                TestScreen(
                    navController = navController,
                    onBackPressed = {
                        navController.navigate(Graph.HOME) {
                            popUpTo(ContentNavScreen.Test.route)
                        }
                    },
                    userViewModel = userViewModel,
                    quesViewModel = quesViewModel,
                    entityViewModel = entityViewModel,
                    examMetaData = data
                )
            }
        }

        composable(
            route = ContentNavScreen.Result.route
                .plus(ContentNavScreen.Result.objectPath)
                .plus(ContentNavScreen.Result.objectPathTwo),
            arguments = listOf(
                navArgument(ContentNavScreen.Result.objectName) {
                    type = NavType.StringType
                },
                navArgument(ContentNavScreen.Result.objectNameTwo) {
                    type = NavType.StringType
                }
            )
        ) {

            val result = it.arguments?.getString(ContentNavScreen.Result.objectName)
            val metaData = it.arguments?.getString(ContentNavScreen.Result.objectNameTwo)

            val testResult = result?.fromPrettyJson<TestResult>()
            val examMetaData = metaData?.fromPrettyJson<ExamMetaData>()

            ResultScreen(
                navController = navController,
                userViewModel = userViewModel,
                testResult = testResult!!,
                examMetaData = examMetaData!!
            )
        }

        composable(
            route = ContentNavScreen.AnswerSheet.route
                .plus(ContentNavScreen.AnswerSheet.objectPath),
            arguments = listOf(
                navArgument(ContentNavScreen.AnswerSheet.objectName) {
                    type = NavType.StringType
                }
            )
        ) {

            val response = it.arguments?.getString(ContentNavScreen.AnswerSheet.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            AnswerSheetScreen(
                quesViewModel = quesViewModel,
                entityViewModel = entityViewModel,
                examMetaData = examMetaData
            )
        }

        composable(route = ContentNavScreen.ResultDashboard.route) {
            ResultDashboard(navController = navController, userViewModel = userViewModel)
        }
    }
}