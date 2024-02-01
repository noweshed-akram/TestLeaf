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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
import com.awsprep.user.domain.models.ExamMetaData
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.utils.AppConstant
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun ChapterScreen(
    navController: NavController,
    asesmntViewModel: AsesmntViewModel,
    examMetaData: ExamMetaData
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var chapterList by rememberSaveable {
        mutableStateOf(emptyList<Course>())
    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getChapterList(examMetaData.courseId!!)

        asesmntViewModel.chaptersData.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d("ChapterScreen: ", "Loading")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d("ChapterScreen: ", it.error)
            }
            it.dataList?.let {
                showProgress = false
                chapterList = it as List<Course>
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = { }) {

            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 20.dp,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(chapterList.size) {
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

                                val examData = ExamMetaData(
                                    examName = examMetaData.examName,
                                    examType = examMetaData.examType,
                                    courseId = examMetaData.courseId,
                                    chapterId = chapterList[it].docId
                                )

                                val gson = Gson()
                                val examMetaDataJson = gson.toJson(examData)

                                navController.navigate(
                                    ContentNavScreen.Sections.route
                                        .plus("/${examMetaDataJson}")
                                )
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
                                        .data(chapterList[it].icon)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .size(52.dp)
                                        .padding(4.dp)
                                        .clip(CircleShape),
                                    error = painterResource(id = R.drawable.ic_error_icon)
                                )
                            }

                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "${it + 1}. ${chapterList[it].name}",
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
                                    text = "10+ Sections",
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