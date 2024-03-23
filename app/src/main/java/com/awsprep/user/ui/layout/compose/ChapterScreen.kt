package com.awsprep.user.ui.layout.compose

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
import com.awsprep.user.domain.models.Course
import com.awsprep.user.domain.models.ExamMetaData
import com.awsprep.user.ui.component.GridItemView
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.publicSansFamily
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun ChapterScreen(
    asesmntViewModel: AsesmntViewModel,
    examMetaData: ExamMetaData,
    onChapterItemClick: (ExamMetaData) -> Unit
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
                items(chapterList.size) {
                    GridItemView(
                        title = "${it + 1}. ${chapterList[it].name}",
                        subTitle = "10+ Sections",
                        iconUrl = chapterList[it].icon
                    ) {
                        val xmMetaData = ExamMetaData(
                            examName = examMetaData.examName + " -> " + chapterList[it].name,
                            examType = examMetaData.examType,
                            courseId = examMetaData.courseId,
                            chapterId = chapterList[it].docId
                        )

                        onChapterItemClick(xmMetaData)
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