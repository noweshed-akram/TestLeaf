package com.awsprep.user.ui.layout.compose.bottombar

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.awsprep.user.ui.component.PrimaryButton
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.component.SetsItemView
import com.awsprep.user.ui.component.VerticalGrid
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.ui.theme.WhiteColor
import com.awsprep.user.viewmodel.AsesmntViewModel
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
    val coroutineScope = rememberCoroutineScope()

    var courseList by rememberSaveable {
        mutableStateOf(emptyList<Course>())
    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getCourseList(6)

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
            it.dataList?.let {
                showProgress = false
                courseList = it as List<Course>
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Courses",
            style = Typography.titleLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        VerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            columns = 2
        ) {
            courseList.forEachIndexed { index, course ->
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            1.dp,
                            StrokeColor,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            navController.navigate(ContentNavScreen.Chapters.route.plus("/${course.docId}"))
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .background(color = StrokeColor, shape = CircleShape)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(course.icon)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(52.dp)
                                    .padding(4.dp),
                                error = painterResource(id = R.drawable.ic_error_icon)
                            )
                        }

                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${index + 1}. ${course.name}",
                            style = Typography.titleMedium,
                            color = Color.Black,
                            maxLines = 2
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                                .background(color = ColorAccent)
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "10+ Chapters",
                                style = Typography.bodySmall,
                                color = Color.Black,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryButton(
            modifier = Modifier.padding(horizontal = 12.dp),
            onClick = {
                navController.navigate(ContentNavScreen.AllCourse.route)
            },
            buttonText = "Explore More Course",
            backgroundColor = WhiteColor,
            fontColor = PrimaryColor,
            borderStrokeColor = PrimaryColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Sets",
            style = Typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        SetsItemView(
            modifier = Modifier.padding(horizontal = 12.dp),
            setsIcon = R.drawable.ic_random,
            title = "Random Test",
            subTitle = "100+ Random Test Sets"
        ) {
            navController.navigate(ContentNavScreen.RandomSets.route)
        }

        Spacer(modifier = Modifier.height(12.dp))

        SetsItemView(
            modifier = Modifier.padding(horizontal = 12.dp),
            setsIcon = R.drawable.ic_edit,
            title = "Practice Test",
            subTitle = "100+ Practice Test Sets"
        ) {
            navController.navigate(ContentNavScreen.PracticeSets.route)
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