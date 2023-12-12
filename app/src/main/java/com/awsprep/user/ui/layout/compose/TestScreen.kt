package com.awsprep.user.ui.layout.compose

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.awsprep.user.R
import com.awsprep.user.domain.models.Feedback
import com.awsprep.user.domain.models.Question
import com.awsprep.user.domain.models.TestResult
import com.awsprep.user.ui.component.MultipleChoiceQues
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.component.SingleChoiceQues
import com.awsprep.user.ui.component.getTransitionDirection
import com.awsprep.user.utils.AppConstant.DATE_TIME_FORMAT
import com.awsprep.user.utils.AppConstant.STATUS_PASS
import com.awsprep.user.utils.getCurrentDateTime
import com.awsprep.user.utils.toString
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.TestViewModel
import com.awsprep.user.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.talhafaki.composablesweettoast.util.SweetToastUtil

private const val CONTENT_ANIMATION_DURATION = 300

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun TestScreen(
    onBackPressed: () -> Unit,
    onSubmitAnswers: () -> Unit,
    activeTimer: Boolean = true,
    userViewModel: UserViewModel,
    quesViewModel: QuesViewModel
) {

    val TAG = "TestScreen"

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var showSuccess by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }
    var successMsg by rememberSaveable { mutableStateOf("") }

    val testViewModel: TestViewModel = viewModel()

    var questionList by rememberSaveable {
        mutableStateOf(emptyList<Question>())
    }

    LaunchedEffect(key1 = true) {
        quesViewModel.questionData.collect {
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
            it.data?.let {
                showProgress = false
                showSuccess = true
                successMsg = "Question added successfully to your review section"
            }
            it.dataList?.let {
                showProgress = false
                questionList = it as List<Question>
                testViewModel.questionOrder = questionList
            }
        }
    }

    val questionIndexData = testViewModel.questionIndexData ?: return

    LaunchedEffect(key1 = true){
        testViewModel.onNextPressed()
        testViewModel.onPreviousPressed()
    }

    QuestionsScreen(
        questionIndexData = questionIndexData,
        isNextEnabled = testViewModel.isNextEnabled,
        activeTimer = activeTimer,
        onBackPressed = onBackPressed,
        onClickToAddReviewQs = {
            quesViewModel.addToReviewQues(questionIndexData.question)
        },
        onFeedbackSend = { msg ->
            quesViewModel.sendQuesFeedback(
                Feedback(
                    userId = FirebaseAuth.getInstance().uid,
                    questionId = questionIndexData.question.quesId,
                    feedback = msg,
                    createdAt = getCurrentDateTime().toString(DATE_TIME_FORMAT),
                    updatedAt = getCurrentDateTime().toString(DATE_TIME_FORMAT)
                )
            )
        },
        onPreviousPressed = { testViewModel.onPreviousPressed() },
        onNextPressed = { testViewModel.onNextPressed() },
        onSubmitPressed = {
            userViewModel.insertTestResult(
                TestResult(
                    testType = "Course",
                    testName = "CompTIA Security+",
                    totalQs = "30",
                    answered = "27",
                    correctAnswered = "20",
                    wrongAnswered = "7",
                    status = STATUS_PASS,
                    createdAt = getCurrentDateTime().toString(DATE_TIME_FORMAT),
                    updatedAt = getCurrentDateTime().toString(DATE_TIME_FORMAT),
                )
            )
            testViewModel.onDonePressed(onSubmitAnswers)
        }
    ) { paddingValues ->

        val modifier = Modifier.padding(paddingValues)

        AnimatedContent(
            targetState = questionIndexData,
            transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)

                val direction = getTransitionDirection(
                    initialIndex = initialState.questionIndex,
                    targetIndex = initialState.questionCount,
                )

                slideIntoContainer(
                    towards = direction,
                    animationSpec = animationSpec,
                ) togetherWith slideOutOfContainer(
                    towards = direction,
                    animationSpec = animationSpec
                )
            },
            label = "dataAnimation"
        ) { targetState ->

            Log.d(TAG, ": " + targetState.toString())

            if (targetState.question.optionE.isNotEmpty()) {
                MultipleChoiceQues(
                    questionTitle = targetState.question.ques,
                    directionsResourceId = R.string.select_all,
                    possibleAnswers = listOf(
                        targetState.question.optionA,
                        targetState.question.optionB,
                        targetState.question.optionC,
                        targetState.question.optionD,
                        targetState.question.optionE
                    ),
                    selectedAnswers = testViewModel.multipleChoiceResponse,
                    onOptionSelected = testViewModel::onMultipleChoiceResponse,
                    modifier = modifier,
                )
            } else {
                SingleChoiceQues(
                    questionTitle = targetState.question.ques,
                    directionsResourceId = R.string.select_one,
                    possibleAnswers = listOf(
                        targetState.question.optionA,
                        targetState.question.optionB,
                        targetState.question.optionC,
                        targetState.question.optionD
                    ),
                    selectedAnswer = testViewModel.singleChoiceResponse,
                    onOptionSelected = testViewModel::onSingleChoiceResponse,
                    modifier = modifier,
                )
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

    if (showSuccess) {
        showSuccess = false
        SweetToastUtil.SweetSuccess(message = successMsg, padding = PaddingValues(10.dp))
    }

}