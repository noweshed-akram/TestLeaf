package com.testleaf.user.ui.layout.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.talhafaki.composablesweettoast.util.SweetToastUtil
import com.testleaf.user.data.remote.model.response.CourseData
import com.testleaf.user.domain.models.ExamMetaData
import com.testleaf.user.ui.component.GridItemView
import com.testleaf.user.ui.component.ProgressBar
import com.testleaf.user.utils.AppConstant.COLL_COURSES
import com.testleaf.user.viewmodel.AsesmntViewModel

/**
 * Created by Md. Noweshed Akram on 17/11/23.
 */
@Composable
fun AllCourseScreen(
    asesmntViewModel: AsesmntViewModel,
    onCourseItemClick: (ExamMetaData) -> Unit
) {

    val TAG = "AllCourseScreen"

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var courseList by rememberSaveable {
        mutableStateOf(emptyList<CourseData>())
    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getCourseList()

        asesmntViewModel.courseResponse.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d(TAG, "Loading")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d(TAG, it.error)
            }
            it.dataList?.let {
                showProgress = false
                courseList = it as List<CourseData>
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) {

        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2)
        ) {
            items(courseList.size) {
                GridItemView(
                    title = "${it + 1}. ${courseList[it].courseName}",
                    subTitle = "10+ Chapters",
                    iconUrl = courseList[it].iconData?.url!!
                ) {
                    val examMetaData = ExamMetaData(
                        examName = courseList[it].courseName,
                        examType = COLL_COURSES,
                        courseId = courseList[it].id.toString()
                    )

                    onCourseItemClick(examMetaData)
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