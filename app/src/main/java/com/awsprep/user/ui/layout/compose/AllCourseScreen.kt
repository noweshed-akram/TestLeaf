package com.awsprep.user.ui.layout.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 17/11/23.
 */
@Composable
fun AllCourseScreen(
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
        asesmntViewModel.getCourseList(50)

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
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(courseList.size) {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            1.dp,
                            StrokeColor,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            navController.navigate(ContentNavScreen.Chapters.route.plus("/${courseList[it].docId}"))
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
                                    .data(courseList[it].icon)
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
                            text = courseList[it].name,
                            style = Typography.bodyMedium,
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

    }

    if (showProgress) {
        ProgressBar()
    }

    if (showError) {
        showError = false
        SweetToastUtil.SweetError(message = errorMsg, padding = PaddingValues(10.dp))
    }
}