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
import com.testleaf.user.domain.models.Course
import com.testleaf.user.domain.models.ExamMetaData
import com.testleaf.user.ui.component.GridItemView
import com.testleaf.user.ui.component.ProgressBar
import com.testleaf.user.utils.AppConstant.COLL_COURSES
import com.testleaf.user.viewmodel.AsesmntViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 17/11/23.
 */
@Composable
fun AllCourseScreen(
    asesmntViewModel: AsesmntViewModel,
    onCourseItemClick: (ExamMetaData) -> Unit
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
            .padding(vertical = 8.dp)
    ) {

        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2)
        ) {
            items(courseList.size) {
                GridItemView(
                    title = "${it + 1}. ${courseList[it].name}",
                    subTitle = "10+ Chapters",
                    iconUrl = courseList[it].icon
                ) {
                    val examMetaData = ExamMetaData(
                        examName = courseList[it].name,
                        examType = COLL_COURSES,
                        courseId = courseList[it].docId
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