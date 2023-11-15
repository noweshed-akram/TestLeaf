package com.awsprep.user.ui.layout.compose.bottombar

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.awsprep.user.R
import com.awsprep.user.domain.models.Course
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.PrimaryColorLight
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 10/11/23.
 */
@Composable
fun AssessmentScreen(
    navController: NavController,
    asesmntViewModel: AsesmntViewModel
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var courseList by rememberSaveable {
        mutableStateOf(emptyList<Course>())
    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getCourseList()

        asesmntViewModel.coursesData.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d("AssessmentScreen: ", "Loading")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d("AssessmentScreen: ", it.error)
            }
            it.data?.let {
                showProgress = false
                courseList = it as List<Course>
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {

        Text(text = "Courses", style = Typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = { }) {

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = courseList
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(150.dp)
                            .height(140.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                PrimaryColorLight,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(10.dp)
                            .clickable {
                                navController.navigate(ContentNavScreen.Chapters.route)
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(it.icon)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(5.dp)),
                                error = painterResource(id = R.drawable.ic_error_icon)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = it.name.trim(),
                                    style = Typography.bodyMedium,
                                    color = Color.Black,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = "10 Chapters",
                                    style = Typography.bodySmall,
                                    color = Color.Black,
                                    maxLines = 1
                                )
                            }

                        }
                    }
                }
            }
        }

    }

    if (showProgress) {
        ProgressBar()
    }

    if (showError) {
        showError = false
        SweetToastUtil.SweetError(message = errorMsg, padding = PaddingValues(10.dp))
    }
}