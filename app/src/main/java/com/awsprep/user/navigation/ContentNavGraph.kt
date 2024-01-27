package com.awsprep.user.navigation

import androidx.core.os.BuildCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.awsprep.user.domain.models.TestResult
import com.awsprep.user.ui.layout.compose.AllCourseScreen
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
            response?.let {
                ChapterScreen(
                    navController = navController,
                    asesmntViewModel = asesmntViewModel,
                    courseId = it
                )
            }
        }

        composable(
            route = ContentNavScreen.Sections.route
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
            route = ContentNavScreen.Timer.route
                .plus(ContentNavScreen.Timer.objectPath)
                .plus(ContentNavScreen.Timer.objectPathTwo)
                .plus(ContentNavScreen.Timer.objectPathThree)
                .plus(ContentNavScreen.Timer.objectPathFour),
            arguments = listOf(
                navArgument(ContentNavScreen.Timer.objectName) {
                    type = NavType.StringType
                },
                navArgument(ContentNavScreen.Timer.objectNameTwo) {
                    type = NavType.StringType
                },
                navArgument(ContentNavScreen.Timer.objectNameThree) {
                    type = NavType.StringType
                },
                navArgument(ContentNavScreen.Timer.objectNameFour) {
                    type = NavType.StringType
                }
            )
        ) {
            val moduleType = it.arguments?.getString(ContentNavScreen.Timer.objectName)
            val course = it.arguments?.getString(ContentNavScreen.Timer.objectNameTwo)
            val chapter = it.arguments?.getString(ContentNavScreen.Timer.objectNameThree)
            val section = it.arguments?.getString(ContentNavScreen.Timer.objectNameFour)

            moduleType?.let { type ->
                course?.let { courseId ->
                    chapter?.let { chapterId ->
                        section?.let { sectionId ->
                            TimerScreen(
                                navController = navController,
                                quesViewModel = quesViewModel,
                                entityViewModel = entityViewModel,
                                moduleType = type,
                                courseId = courseId,
                                chapterId = chapterId,
                                sectionId = sectionId
                            )
                        }
                    }
                }
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
            route = ContentNavScreen.SubSets.route.plus(ContentNavScreen.SubSets.objectPath)
                .plus(ContentNavScreen.SubSets.objectPathTwo),
            arguments = listOf(
                navArgument(ContentNavScreen.SubSets.objectName) {
                    type = NavType.StringType
                },
                navArgument(ContentNavScreen.SubSets.objectNameTwo) {
                    type = NavType.StringType
                }
            )
        ) {
            val setId = it.arguments?.getString(ContentNavScreen.SubSets.objectName, "")
            val subSetId = it.arguments?.getString(ContentNavScreen.SubSets.objectNameTwo, "")

            setId?.let {
                subSetId?.let {
                    SubSetsScreen(
                        navController = navController,
                        asesmntViewModel = asesmntViewModel,
                        setId = setId,
                        subSetId = subSetId
                    )
                }
            }

        }

        composable(
            route = ContentNavScreen.Test.route.plus(ContentNavScreen.Test.objectPath),
            arguments = listOf(
                navArgument(ContentNavScreen.Test.objectName) {
                    type = NavType.BoolType
                }
            )
        ) {

            val activeTimer = it.arguments?.getBoolean(ContentNavScreen.Test.objectName, true)

            activeTimer?.let {
                TestScreen(
                    navController = navController,
                    onBackPressed = {
                        navController.navigate(Graph.HOME) {
                            popUpTo(ContentNavScreen.Test.route)
                        }
                    },
                    activeTimer = it,
                    userViewModel = userViewModel,
                    quesViewModel = quesViewModel,
                    entityViewModel = entityViewModel
                )
            }
        }

        composable(
            route = ContentNavScreen.Result.route.plus(ContentNavScreen.Result.objectPath),
            arguments = listOf(navArgument(ContentNavScreen.Result.objectName) {
                type = NavType.StringType
            })
        ) {

            val result = it.arguments?.getString(ContentNavScreen.Result.objectName)

            val testResult = result?.fromPrettyJson<TestResult>()

            ResultScreen(
                navController = navController,
                userViewModel = userViewModel,
                testResult = testResult!!
            )
        }

        composable(route = ContentNavScreen.ResultDashboard.route) {
            ResultDashboard(navController = navController, userViewModel = userViewModel)
        }

    }

}