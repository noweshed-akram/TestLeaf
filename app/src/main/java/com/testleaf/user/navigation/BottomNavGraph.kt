package com.testleaf.user.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testleaf.user.ui.layout.compose.bottombar.AcronymsScreen
import com.testleaf.user.ui.layout.compose.bottombar.AssessmentScreen
import com.testleaf.user.ui.layout.compose.bottombar.DefinitionScreen
import com.testleaf.user.ui.layout.compose.bottombar.LessonsScreen
import com.testleaf.user.utils.toPrettyJson
import com.testleaf.user.viewmodel.AsesmntViewModel
import com.testleaf.user.viewmodel.AuthViewModel
import com.testleaf.user.viewmodel.EntityViewModel
import com.testleaf.user.viewmodel.QuesViewModel
import com.testleaf.user.viewmodel.UserViewModel

/**
 * Created by noweshedakram on 16/8/23.
 */
@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = BottomNavScreen.Assessment.route,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel,
    quesViewModel: QuesViewModel,
    entityViewModel: EntityViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        route = Graph.HOME
    ) {

        composable(route = BottomNavScreen.Assessment.route) {
            AssessmentScreen(
                asesmntViewModel = asesmntViewModel,
                onCourseItemClick = { examMetaData ->
                    navController.navigate(
                        ContentNavScreen.Chapters.route
                            .plus("/${examMetaData.toPrettyJson()}")
                    )
                },
                onSetItemClick = { examMetaData ->
                    navController.navigate(
                        ContentNavScreen.SubSets.route
                            .plus("/${examMetaData.toPrettyJson()}")
                    )
                },
                onAllCourseBtnClick = {
                    navController.navigate(ContentNavScreen.AllCourse.route)
                },
                onReviewQuesClick = {
                    navController.navigate(ContentNavScreen.ReviewQues.route)
                }
            )
        }

        composable(route = BottomNavScreen.Lessons.route) {
            LessonsScreen(
                userViewModel = userViewModel
            )
        }

        composable(route = BottomNavScreen.Acronyms.route) {
            AcronymsScreen(
                userViewModel = userViewModel,
                asesmntViewModel = asesmntViewModel
            )
        }

        composable(route = BottomNavScreen.Definition.route) {
            DefinitionScreen(
                userViewModel = userViewModel,
                asesmntViewModel = asesmntViewModel
            )
        }

//        composable(route = BottomNavScreen.MyProfile.route) {
//            MyProfileScreen(
//                userViewModel = userViewModel,
//                onEditBtnClick = {
//                    navController.navigate(ContentNavScreen.EditProfile.route)
//                },
//                onDashboardBtnClick = {
//                    navController.navigate(ContentNavScreen.ResultDashboard.route)
//                }
//            )
//        }

        ContentNavGraph(
            navController = navController,
            authViewModel = authViewModel,
            userViewModel = userViewModel,
            asesmntViewModel = asesmntViewModel,
            quesViewModel = quesViewModel,
            entityViewModel = entityViewModel
        )

        AuthNavGraph(
            navController = navController,
            authViewModel = authViewModel,
            entityViewModel = entityViewModel
        )
    }
}