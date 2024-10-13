package com.testleaf.user.navigation

import androidx.core.os.BuildCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.testleaf.user.domain.models.ExamMetaData
import com.testleaf.user.domain.models.TestResult
import com.testleaf.user.ui.layout.compose.AllCourseScreen
import com.testleaf.user.ui.layout.compose.AnswerSheetScreen
import com.testleaf.user.ui.layout.compose.ChapterScreen
import com.testleaf.user.ui.layout.compose.EditProfileScreen
import com.testleaf.user.ui.layout.compose.NotificationScreen
import com.testleaf.user.ui.layout.compose.SubSetsScreen
import com.testleaf.user.ui.layout.compose.ResultDashboard
import com.testleaf.user.ui.layout.compose.ResultScreen
import com.testleaf.user.ui.layout.compose.ReviewQuesScreen
import com.testleaf.user.ui.layout.compose.SectionScreen
import com.testleaf.user.ui.layout.compose.TestScreen
import com.testleaf.user.ui.layout.compose.TimerScreen
import com.testleaf.user.ui.layout.compose.MyProfileScreen
import com.testleaf.user.ui.layout.compose.SubscribeScreen
import com.testleaf.user.ui.layout.compose.VideoLectureScreen
import com.testleaf.user.utils.fromPrettyJson
import com.testleaf.user.utils.toPrettyJson
import com.testleaf.user.viewmodel.AsesmntViewModel
import com.testleaf.user.viewmodel.EntityViewModel
import com.testleaf.user.viewmodel.QuesViewModel
import com.testleaf.user.viewmodel.UserViewModel
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
            EditProfileScreen(userViewModel = userViewModel)
        }

        composable(ContentNavScreen.Notification.route) {
            NotificationScreen()
        }

        composable(ContentNavScreen.AllCourse.route) {
            AllCourseScreen(
                asesmntViewModel = asesmntViewModel,
                onCourseItemClick = { examMetaData ->
                    navController.navigate(
                        ContentNavScreen.Chapters.route.plus("/${examMetaData.toPrettyJson()}")
                    )
                })
        }

        composable(
            route = ContentNavScreen.Chapters.route.plus(ContentNavScreen.Chapters.objectPath),
            arguments = listOf(navArgument(ContentNavScreen.Chapters.objectName) {
                type = NavType.StringType
            }),
        ) {

            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            examMetaData?.let { data ->
                ChapterScreen(
                    asesmntViewModel = asesmntViewModel,
                    examMetaData = data,
                    onChapterItemClick = { xmMetaData ->
                        navController.navigate(
                            ContentNavScreen.Sections.route.plus("/${xmMetaData.toPrettyJson()}")
                        )
                    })
            }
        }

        composable(
            route = ContentNavScreen.Sections.route.plus(ContentNavScreen.Sections.objectPath),
            arguments = listOf(navArgument(ContentNavScreen.Sections.objectName) {
                type = NavType.StringType
            })
        ) {

            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            examMetaData?.let { data ->
                SectionScreen(
                    asesmntViewModel = asesmntViewModel,
                    examMetaData = data,
                    onSectionItemClick = { xmMetaData ->
                        navController.navigate(
                            ContentNavScreen.Timer.route.plus("/${xmMetaData.toPrettyJson()}")
                        )
                    })
            }

        }

        composable(
            route = ContentNavScreen.Timer.route.plus(ContentNavScreen.Timer.objectPath),
            arguments = listOf(navArgument(ContentNavScreen.Timer.objectName) {
                type = NavType.StringType
            })
        ) {

            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            examMetaData?.let { data ->
                TimerScreen(
                    quesViewModel = quesViewModel,
                    entityViewModel = entityViewModel,
                    examMetaData = data,
                    onStartBtnClick = { xmMetaData ->
                        navController.navigate(
                            ContentNavScreen.Test.route
                                .plus("/${xmMetaData.toPrettyJson()}")
                        )
                    }
                )
            }
        }

        composable(route = ContentNavScreen.ReviewQues.route) {
            ReviewQuesScreen(
                quesViewModel = quesViewModel,
                entityViewModel = entityViewModel
            )
        }

        composable(
            route = ContentNavScreen.SubSets.route.plus(ContentNavScreen.SubSets.objectPath),
            arguments = listOf(navArgument(ContentNavScreen.SubSets.objectName) {
                type = NavType.StringType
            })
        ) {
            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            examMetaData?.let { data ->
                SubSetsScreen(
                    asesmntViewModel = asesmntViewModel,
                    examMetaData = data,
                    onSubsetItemClick = { xmMetaData ->
                        navController.navigate(
                            ContentNavScreen.Timer.route.plus("/${xmMetaData.toPrettyJson()}")
                        )
                    })
            }

        }

        composable(
            route = ContentNavScreen.Test.route.plus(ContentNavScreen.Test.objectPath),
            arguments = listOf(navArgument(ContentNavScreen.Test.objectName) {
                type = NavType.StringType
            })
        ) {

            val response = it.arguments?.getString(ContentNavScreen.Chapters.objectName)
            val gson = Gson()
            val examMetaData = gson.fromJson(response, ExamMetaData::class.java)

            examMetaData?.let { data ->
                TestScreen(
                    userViewModel = userViewModel,
                    quesViewModel = quesViewModel,
                    entityViewModel = entityViewModel,
                    examMetaData = data,
                    onBackPressed = {
                        navController.navigate(Graph.HOME) {
                            popUpTo(ContentNavScreen.Test.route)
                        }
                    },
                    onSubmitTestBtnClick = { testResult, xmData ->
                        navController.navigate(
                            ContentNavScreen.Result.route
                                .plus("/${testResult.toPrettyJson()}")
                                .plus("/${xmData.toPrettyJson()}")
                        ) {
                            popUpTo(ContentNavScreen.Test.route)
                        }
                    }
                )
            }
        }

        composable(
            route = ContentNavScreen.Result.route.plus(ContentNavScreen.Result.objectPath)
                .plus(ContentNavScreen.Result.objectPathTwo),
            arguments = listOf(navArgument(ContentNavScreen.Result.objectName) {
                type = NavType.StringType
            }, navArgument(ContentNavScreen.Result.objectNameTwo) {
                type = NavType.StringType
            })
        ) {

            val result = it.arguments?.getString(ContentNavScreen.Result.objectName)
            val metaData = it.arguments?.getString(ContentNavScreen.Result.objectNameTwo)

            val testResult = result?.fromPrettyJson<TestResult>()
            val examMetaData = metaData?.fromPrettyJson<ExamMetaData>()

            ResultScreen(
                userViewModel = userViewModel,
                testResult = testResult!!,
                examMetaData = examMetaData!!,
                onBackBtnClick = {
                    navController.navigate(BottomNavScreen.Assessment.route) {
                        popUpTo(BottomNavScreen.Assessment.route) {
                            inclusive = true
                        }
                    }
                },
                onHomeBtnClick = {
                    navController.navigate(BottomNavScreen.Assessment.route) {
                        popUpTo(BottomNavScreen.Assessment.route) {
                            inclusive = true
                        }
                    }
                },
                onRetakeBtnClick = { xmData ->
                    navController.navigate(
                        ContentNavScreen.Timer.route
                            .plus("/${xmData.toPrettyJson()}")
                    )
                },
                onCheckAnswersBtnClick = { xmData ->
                    navController.navigate(
                        ContentNavScreen.AnswerSheet.route
                            .plus("/${xmData.toPrettyJson()}")
                    )
                }
            )
        }

        composable(
            route = ContentNavScreen.AnswerSheet.route.plus(ContentNavScreen.AnswerSheet.objectPath),
            arguments = listOf(navArgument(ContentNavScreen.AnswerSheet.objectName) {
                type = NavType.StringType
            })
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

        composable(route = ContentNavScreen.MyProfile.route) {
            MyProfileScreen(
                userViewModel = userViewModel,
                onEditBtnClick = {
                    navController.navigate(ContentNavScreen.EditProfile.route)
                },
                onDashboardBtnClick = {
                    navController.navigate(ContentNavScreen.ResultDashboard.route)
                },
                onClickSubs = {
                    navController.navigate(ContentNavScreen.SubscribePage.route)
                }
            )
        }

        composable(route = ContentNavScreen.ResultDashboard.route) {
            ResultDashboard(userViewModel = userViewModel)
        }

        composable(route = ContentNavScreen.SubscribePage.route) {
            SubscribeScreen(userViewModel = userViewModel)
        }

        composable(route = ContentNavScreen.VideoLecture.route) {
            VideoLectureScreen()
        }

    }
}