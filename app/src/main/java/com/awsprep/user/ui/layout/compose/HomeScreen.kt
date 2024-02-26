package com.awsprep.user.ui.layout.compose

import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.awsprep.user.R
import com.awsprep.user.domain.models.BottomMenuContent
import com.awsprep.user.domain.models.NavItem
import com.awsprep.user.navigation.AuthScreen
import com.awsprep.user.navigation.BottomNavScreen
import com.awsprep.user.navigation.BottomNavigation
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.navigation.currentRoute
import com.awsprep.user.ui.component.AppBarWithArrow
import com.awsprep.user.ui.component.BottomMenu
import com.awsprep.user.ui.component.DrawerBody
import com.awsprep.user.ui.component.DrawerHeader
import com.awsprep.user.ui.component.HomeTopView
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.utils.openContentLink
import com.awsprep.user.utils.shareApp
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.AuthViewModel
import com.awsprep.user.viewmodel.EntityViewModel
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.UserViewModel
import kotlinx.coroutines.launch


/**
 * Created by noweshedakram on 24/6/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel,
    quesViewModel: QuesViewModel,
    entityViewModel: EntityViewModel
) {

    val context = LocalContext.current
    var userName by rememberSaveable { mutableStateOf("") }
    var userEmail by rememberSaveable { mutableStateOf("") }
    var userProfileImage by rememberSaveable { mutableStateOf("") }
    var versionName by rememberSaveable { mutableStateOf("") }

    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }
    var topBarState by rememberSaveable { (mutableStateOf(true)) }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    try {
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        versionName = pInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    LaunchedEffect(key1 = true) {

        userViewModel.getUserData()

        userViewModel.userData.collect { it ->
            if (it.error.isNotBlank()) {
                Log.d("EmailSignScreen: ", it.error)
                navController.navigate(AuthScreen.EmailSignIn.route)
            }
            it.data?.let {
                userName = it.name
                userEmail = it.email
                userProfileImage = it.image
            }
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        BottomNavScreen.Assessment.route -> {
            // Show BottomBar and TopBar
            bottomBarState = true
            topBarState = true
        }

        BottomNavScreen.Lessons.route, BottomNavScreen.Acronyms.route, BottomNavScreen.Definition.route -> {
            bottomBarState = true
            topBarState = false
        }

        else -> {
            bottomBarState = false
            topBarState = false
        }
    }

    ModalNavigationDrawer(drawerState = drawerState,
        scrimColor = StrokeColor.copy(.5f),
        drawerContent = {
            Column {
                DrawerHeader(
                    userName = userName, userEmail = userEmail, userImage = userProfileImage
                )
                DrawerBody(modifier = Modifier
                    .weight(1.0f)
                    .width(240.dp)
                    .fillMaxHeight()
                    .background(color = StrokeColor), items = listOf(
                    NavItem(
                        id = "subscribe",
                        title = "Become a pro member",
                        contentDescription = "subscribe",
                        icon = Icons.Default.Person4
                    ), NavItem(
                        id = "share",
                        title = "Share",
                        contentDescription = "share with your networks",
                        icon = Icons.Default.Share
                    ), NavItem(
                        id = "terms_conditions",
                        title = "Terms & Conditions",
                        contentDescription = "Terms & Conditions",
                        icon = Icons.Default.Policy
                    ), NavItem(
                        id = "about_us",
                        title = "About Us",
                        contentDescription = "About Us",
                        icon = Icons.Default.Info
                    ), NavItem(
                        id = "rate",
                        title = "Rate",
                        contentDescription = "Rate",
                        icon = Icons.Default.StarRate
                    ), NavItem(
                        id = "feedback",
                        title = "Feedback",
                        contentDescription = "Feedback",
                        icon = Icons.Default.Feedback
                    ), NavItem(
                        id = "log_out",
                        title = "Log Out",
                        contentDescription = "Go to login",
                        icon = Icons.Default.Logout
                    )
                ), onItemClick = {
                    scope.launch {
                        drawerState.close()
                    }
                    when (it.id) {
                        "subscribe" -> {
                            println("Subscribe")
                            navController.navigate(ContentNavScreen.SubscribePage.route)
                        }

                        "share" -> {
                            println("Share")
                            shareApp(context)
                            navController.navigate(AuthScreen.EmailSignIn.route)
                        }

                        "terms_conditions" -> {
                            println("Terms & Conditions")
                            openContentLink(context, "https://www.google.com")
                        }

                        "about_us" -> {
                            println("About Us")
                            openContentLink(context, "www.google.com")
                        }

                        "feedback" -> {
                            println("Feedback")
                            openContentLink(context, "www.google.com")
                        }

                        "rate" -> {
                            println("Rate")
                            openContentLink(context, "www.google.com")
                        }

                        "log_out" -> {
                            println("Log Out")
                            userViewModel.logOut()
                            navController.navigate(AuthScreen.EmailSignIn.route)
                        }

                        else -> {}
                    }
                })
                Text(
                    text = "v$versionName",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(250.dp)
                        .height(32.dp)
                        .background(color = StrokeColor)
                )
            }
        }) {
        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            if (topBarState) {
                HomeTopView(userName = userName, userImage = userProfileImage, onNavigationClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }, onNotificationClick = {
                    navController.navigate(ContentNavScreen.Notification.route)
                }, onProfileImageClick = {
                    navController.navigate(ContentNavScreen.MyProfile.route)
                })
            } else {
                if (currentRoute(navController = navController) == ContentNavScreen.MyProfile.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "My Profile"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.EditProfile.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Edit Profile"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.Notification.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Notification"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.AllCourse.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "All Course"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == BottomNavScreen.Lessons.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Lesson's"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == BottomNavScreen.Acronyms.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Acronym's"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == BottomNavScreen.Definition.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Definition's"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.Chapters.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Chapters"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.Sections.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Sections"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.ReviewQues.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Review Questions"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.SubSets.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Sets"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.Timer.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Time Selection"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.Result.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Test Result"
                    ) {
                        navController.navigate(BottomNavScreen.Assessment.route) {
                            popUpTo(BottomNavScreen.Assessment.route)
                        }
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.AnswerSheet.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Answer Sheet"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.ResultDashboard.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Result Dashboard"
                    ) {
                        navController.popBackStack()
                    }
                } else if (currentRoute(navController = navController) == ContentNavScreen.SubscribePage.route) {
                    AppBarWithArrow(
                        scrollBehavior = scrollBehavior, title = "Subscriptions"
                    ) {
                        navController.popBackStack()
                    }
                }
            }
        }, bottomBar = {
            if (bottomBarState) {
                BottomMenu(
                    items = listOf(
                        BottomMenuContent(
                            "Assessment",
                            BottomNavScreen.Assessment.route,
                            R.drawable.ic_assesment,
                            R.drawable.ic_assesment
                        ),
                        BottomMenuContent(
                            "Lesson's",
                            BottomNavScreen.Lessons.route,
                            R.drawable.ic_book_close,
                            R.drawable.ic_book
                        ),
                        BottomMenuContent(
                            "Acronym's",
                            BottomNavScreen.Acronyms.route,
                            R.drawable.ic_acronyms,
                            R.drawable.ic_dictionary
                        ),
                        BottomMenuContent(
                            "Definition's",
                            BottomNavScreen.Definition.route,
                            R.drawable.ic_definition,
                            R.drawable.ic_definition
                        ),
//                        BottomMenuContent(
//                            "Profile",
//                            BottomNavScreen.MyProfile.route,
//                            R.drawable.ic_outline_person,
//                            R.drawable.ic_person
//                        ),
                    ), navController = navController
                )
            }
        }) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.padding(
                    top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()
                )
            ) {
                BottomNavigation(
                    navController = navController,
                    startDestination = BottomNavScreen.Assessment.route,
                    authViewModel = authViewModel,
                    userViewModel = userViewModel,
                    asesmntViewModel = asesmntViewModel,
                    quesViewModel = quesViewModel,
                    entityViewModel = entityViewModel
                )
            }
        }
    }


}