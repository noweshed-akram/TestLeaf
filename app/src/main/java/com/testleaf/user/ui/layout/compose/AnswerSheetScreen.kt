package com.testleaf.user.ui.layout.compose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.testleaf.user.domain.models.ExamMetaData
import com.testleaf.user.domain.models.Question
import com.testleaf.user.ui.component.ProgressBar
import com.testleaf.user.ui.component.QuesAnsListItem
import com.testleaf.user.utils.AppConstant
import com.testleaf.user.viewmodel.EntityViewModel
import com.testleaf.user.viewmodel.QuesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 2/2/24.
 */
@Composable
fun AnswerSheetScreen(
    quesViewModel: QuesViewModel,
    entityViewModel: EntityViewModel,
    examMetaData: ExamMetaData
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var questionList by rememberSaveable {
        mutableStateOf(emptyList<Question>())
    }

    LaunchedEffect(key1 = true) {

        Log.d(
            "TimerScreen: ",
            "${examMetaData.courseId} ${examMetaData.chapterId} ${examMetaData.sectionId}"
        )

//        if (examMetaData.examType == AppConstant.COLL_COURSES) {
//            quesViewModel.getQuestions(
//                examMetaData.courseId!!,
//                examMetaData.chapterId!!,
//                examMetaData.sectionId!!,
//                30
//            )
//        } else if (examMetaData.examType == AppConstant.COLL_SETS) {
//            quesViewModel.getQuestions(
//                examMetaData.setId!!,
//                examMetaData.setFlag!!,
//                examMetaData.subsetId!!
//            )
//        }
//
//        quesViewModel.questionData.collect {
//            if (it.isLoading) {
//                showProgress = true
//                Log.d("TimerScreen: ", "Loading")
//            }
//            if (it.error.isNotBlank()) {
//                showProgress = false
//                showError = true
//                errorMsg = it.error
//                Log.d("TimerScreen: ", it.error)
//            }
//            it.dataList?.let {
//                showProgress = false
//                questionList = it as List<Question>
//            }
//        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        if (questionList.isNotEmpty()) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = false),
                onRefresh = {

                }) {
                LazyColumn(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        items = questionList
                    ) { index, question ->
                        QuesAnsListItem(
                            quesNo = index + 1,
                            question = question,
                            deleteBtnVisibility = false
                        ) { quesId ->
//                            quesViewModel.deleteReviewQues(quesId)
                        }
                    }
                }
            }
        } else {
//            InfoBannerCard(
//                icon = R.drawable.ic_question_mark,
//                titleText = "No question found",
//                infoText = "You can add review questions during Test",
//                strokeColor = StrokeColor,
//                bgColor = WhiteColor,
//                onCardClick = {}
//            )
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