package com.testleaf.user.ui.layout.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testleaf.user.domain.models.Course
import com.testleaf.user.domain.models.ExamMetaData
import com.testleaf.user.ui.component.GridItemView
import com.testleaf.user.ui.component.ProgressBar
import com.testleaf.user.ui.theme.publicSansFamily
import com.testleaf.user.viewmodel.AsesmntViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun SectionScreen(
    asesmntViewModel: AsesmntViewModel,
    examMetaData: ExamMetaData,
    onSectionItemClick: (ExamMetaData) -> Unit
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var sectionsList by rememberSaveable {
        mutableStateOf(emptyList<Course>())
    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getSectionList(examMetaData.courseId!!, examMetaData.chapterId!!)

        asesmntViewModel.sectionsData.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d("SectionScreen: ", "Loading")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d("SectionScreen: ", it.error)
            }
            it.dataList?.let {
                showProgress = false
                sectionsList = it as List<Course>
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp)
    ) {

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = examMetaData.examName!!,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            maxLines = 1
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = { }) {

            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2)
            ) {
                items(sectionsList.size) {
                    GridItemView(
                        title = "${it + 1}. ${sectionsList[it].name}",
                        subTitle = "1000+ Questions",
                        iconUrl = sectionsList[it].icon
                    ) {
                        val xmMetaData = ExamMetaData(
                            examName = examMetaData.examName,
                            examType = examMetaData.examType,
                            courseId = examMetaData.courseId,
                            chapterId = examMetaData.chapterId,
                            sectionId = sectionsList[it].docId
                        )

                        onSectionItemClick(xmMetaData)
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