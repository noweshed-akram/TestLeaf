package com.awsprep.user.ui.layout.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.awsprep.user.R
import com.awsprep.user.domain.models.BottomMenuContent
import com.awsprep.user.domain.models.NavItem
import com.awsprep.user.navigation.BottomNavScreen
import com.awsprep.user.navigation.BottomNavigation
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.navigation.CurrentRoute
import com.awsprep.user.ui.component.AppBarWithArrow
import com.awsprep.user.ui.component.BottomMenu
import com.awsprep.user.ui.component.DrawerBody
import com.awsprep.user.ui.component.DrawerHeader
import com.awsprep.user.ui.component.HomeTopView
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.UserViewModel
import kotlinx.coroutines.launch

/**
 * Created by noweshedakram on 24/6/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel,
    quesViewModel: QuesViewModel
) {

    val context = LocalContext.current
    var userName by rememberSaveable { mutableStateOf("") }

    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }
    var topBarState by rememberSaveable { (mutableStateOf(true)) }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    LaunchedEffect(key1 = true) {
//        userViewModel.insertTestResult(
//            "", TestResult(
//                testType = "Course",
//                testName = "CompTIA Security+",
//                totalQs = "60",
//                answered = "46",
//                correctAnswered = "38",
//                wrongAnswered = "22",
//                status = "Incomplete",
//                createdAt = getCurrentDateTime().toString(DATE_TIME_FORMAT),
//                updatedAt = getCurrentDateTime().toString(DATE_TIME_FORMAT),
//            )
//        )

        userViewModel.getUserData()
        userViewModel.userData.collect { it ->
            it.data?.let {
                userName = it.name
            }
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        BottomNavScreen.Assessment.route, BottomNavScreen.Practices.route,
        BottomNavScreen.Acronyms.route, BottomNavScreen.Definition.route,
        BottomNavScreen.MyProfile.route -> {
            // Show BottomBar and TopBar
            bottomBarState = true
            topBarState = true
        }

        else -> {
            bottomBarState = false
            topBarState = false
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = StrokeColor.copy(.3f),
        drawerContent = {
            Column {
                DrawerHeader()
                DrawerBody(
                    modifier = Modifier
                        .weight(1.0f)
                        .width(250.dp)
                        .fillMaxHeight()
                        .background(color = StrokeColor),
                    items = listOf(
                        NavItem(
                            id = "log_out",
                            title = "Log Out",
                            contentDescription = "Go to login",
                            icon = Icons.Default.Logout
                        )
                    ),
                    onItemClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        when (it.id) {

                            "log_out" -> {
                                println("Log Out")
                                userViewModel.logOut()
                            }

                            else -> {}
                        }
                    }
                )
                Text(
                    text = "v1.0.0",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(250.dp)
                        .height(32.dp)
                        .background(color = StrokeColor)
                )
            }
        }) {
        Scaffold(
            topBar = {
                if (topBarState) {
                    HomeTopView(
                        userName = userName,
                        onNavigationClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                        onNotificationClick = {
                            navController.navigate(ContentNavScreen.Notification.route)
                        }
                    )
                } else {
                    if (CurrentRoute(navController = navController) == ContentNavScreen.EditProfile.route) {
                        AppBarWithArrow(title = "Edit Profile") {
                            navController.popBackStack()
                        }
                    } else if (CurrentRoute(navController = navController) == ContentNavScreen.Notification.route) {
                        AppBarWithArrow(title = "Notification") {
                            navController.popBackStack()
                        }
                    } else if (CurrentRoute(navController = navController) == ContentNavScreen.AllCourse.route) {
                        AppBarWithArrow(title = "All Course") {
                            navController.popBackStack()
                        }
                    } else if (CurrentRoute(navController = navController) == ContentNavScreen.Chapters.route) {
                        AppBarWithArrow(title = "Chapters") {
                            navController.popBackStack()
                        }
                    } else if (CurrentRoute(navController = navController) == ContentNavScreen.Sections.route) {
                        AppBarWithArrow(title = "Sections") {
                            navController.popBackStack()
                        }
                    } else if (CurrentRoute(navController = navController) == ContentNavScreen.ReviewQues.route) {
                        AppBarWithArrow(title = "Review Questions") {
                            navController.popBackStack()
                        }
                    } else if (CurrentRoute(navController = navController) == ContentNavScreen.RandomSets.route) {
                        AppBarWithArrow(title = "Random Sets") {
                            navController.popBackStack()
                        }
                    } else if (CurrentRoute(navController = navController) == ContentNavScreen.PracticeSets.route) {
                        AppBarWithArrow(title = "Practice Sets") {
                            navController.popBackStack()
                        }
                    } else if (CurrentRoute(navController = navController) == ContentNavScreen.Timer.route) {
                        AppBarWithArrow(title = "Timer") {
                            navController.popBackStack()
                        }
                    } else if (CurrentRoute(navController = navController) == ContentNavScreen.Result.route) {
                        AppBarWithArrow(title = "Test Score") {
                            navController.popBackStack()
                        }
                    }
                }
            },
            bottomBar = {
                if (bottomBarState) {
                    BottomMenu(
                        items = listOf(
                            BottomMenuContent(
                                "Assessment",
                                BottomNavScreen.Assessment.route,
                                R.drawable.ic_assesment, R.drawable.ic_assesment
                            ),
                            BottomMenuContent(
                                "Practices",
                                BottomNavScreen.Practices.route,
                                R.drawable.ic_qna, R.drawable.ic_qna
                            ),
                            BottomMenuContent(
                                "Acronyms",
                                BottomNavScreen.Acronyms.route,
                                R.drawable.ic_acronyms, R.drawable.ic_acronyms
                            ),
                            BottomMenuContent(
                                "Definition",
                                BottomNavScreen.Definition.route,
                                R.drawable.ic_definition, R.drawable.ic_definition
                            ),
                            BottomMenuContent(
                                "Profile",
                                BottomNavScreen.MyProfile.route,
                                R.drawable.ic_person, R.drawable.ic_person
                            ),
                        ),
                        navController = navController
                    )
                }
            }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
            ) {
                BottomNavigation(
                    navController = navController,
                    startDestination = BottomNavScreen.Assessment.route,
                    userViewModel = userViewModel,
                    asesmntViewModel = asesmntViewModel,
                    quesViewModel = quesViewModel
                )
            }
        }
    }


}