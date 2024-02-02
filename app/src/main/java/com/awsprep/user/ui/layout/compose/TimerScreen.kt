package com.awsprep.user.ui.layout.compose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.awsprep.user.R
import com.awsprep.user.domain.models.ExamMetaData
import com.awsprep.user.domain.models.Question
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.ui.component.InfoBannerCard
import com.awsprep.user.ui.component.PrimaryButton
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.WhiteColor
import com.awsprep.user.utils.AppConstant.COLL_COURSES
import com.awsprep.user.utils.AppConstant.COLL_SETS
import com.awsprep.user.utils.toPrettyJson
import com.awsprep.user.viewmodel.EntityViewModel
import com.awsprep.user.viewmodel.QuesViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun TimerScreen(
    navController: NavController,
    quesViewModel: QuesViewModel,
    entityViewModel: EntityViewModel,
    examMetaData: ExamMetaData
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var activeTimeBaseCard by rememberSaveable { mutableStateOf(true) }

    var questionList by rememberSaveable {
        mutableStateOf(emptyList<Question>())
    }

    LaunchedEffect(key1 = true) {
        entityViewModel.clearLocalDb()
    }

    LaunchedEffect(key1 = true) {

        Log.d(
            "TimerScreen: ",
            "${examMetaData.courseId} ${examMetaData.chapterId} ${examMetaData.sectionId}"
        )

        if (examMetaData.examType == COLL_COURSES) {
            quesViewModel.getQuestions(
                examMetaData.courseId!!,
                examMetaData.chapterId!!,
                examMetaData.sectionId!!,
                30
            )
        } else if (examMetaData.examType == COLL_SETS) {
            quesViewModel.getQuestions(
                examMetaData.setId!!,
                examMetaData.setFlag!!,
                examMetaData.subsetId!!
            )
        }

        quesViewModel.questionData.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d("TimerScreen: ", "Loading")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d("TimerScreen: ", it.error)
            }
            it.dataList?.let {
                showProgress = false
                questionList = it as List<Question>
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        InfoBannerCard(
            icon = R.drawable.ic_stopwatch,
            titleText = "Time Base",
            infoText = "${questionList.size} Minutes | ${questionList.size} Questions",
            strokeColor = if (activeTimeBaseCard) PrimaryColor else StrokeColor,
            bgColor = if (activeTimeBaseCard) ColorAccent else WhiteColor
        ) {
            activeTimeBaseCard = true
        }

        InfoBannerCard(
            icon = R.drawable.ic_stopwatch_off,
            titleText = "No Time Limit",
            infoText = "${questionList.size} Questions",
            strokeColor = if (!activeTimeBaseCard) PrimaryColor else StrokeColor,
            bgColor = if (!activeTimeBaseCard) ColorAccent else WhiteColor
        ) {
            activeTimeBaseCard = false
        }

        PrimaryButton(
            onClick = {
                if (questionList.size <= 2) {
                    showError = true
                    errorMsg = "This section isn't available for Test! Please try later."
                } else {

                    val xmMetaData = ExamMetaData(
                        examName = examMetaData.examName,
                        examType = examMetaData.examType,
                        activeTimer = activeTimeBaseCard,
                        courseId = examMetaData.courseId,
                        chapterId = examMetaData.chapterId,
                        sectionId = examMetaData.sectionId,
                        setId = examMetaData.setId,
                        setFlag = examMetaData.setFlag,
                        subsetId = examMetaData.subsetId
                    )

                    navController.navigate(
                        ContentNavScreen.Test.route
                            .plus("/${xmMetaData.toPrettyJson()}")
                    )
                }
            },
            buttonText = "Start"
        )
    }

    if (showProgress) {
        ProgressBar()
    }

    if (showError) {
        showError = false
        SweetToastUtil.SweetError(message = errorMsg, padding = PaddingValues(10.dp))
    }

}