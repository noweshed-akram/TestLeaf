package com.awsprep.user.ui.layout.compose

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.awsprep.user.R
import com.awsprep.user.domain.models.Feedback
import com.awsprep.user.domain.models.Question
import com.awsprep.user.domain.models.TestResult
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.ui.component.AlertDialog
import com.awsprep.user.ui.component.MultipleChoiceQues
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.component.SingleChoiceQues
import com.awsprep.user.ui.component.TestSubmitAlertDialog
import com.awsprep.user.ui.component.getTransitionDirection
import com.awsprep.user.ui.theme.ErrorColor
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.utils.AppConstant.DATE_TIME_FORMAT
import com.awsprep.user.utils.AppConstant.STATUS_FAILED
import com.awsprep.user.utils.AppConstant.STATUS_PASS
import com.awsprep.user.utils.getCurrentDateTime
import com.awsprep.user.utils.toPrettyJson
import com.awsprep.user.utils.toString
import com.awsprep.user.viewmodel.EntityViewModel
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.TestViewModel
import com.awsprep.user.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.talhafaki.composablesweettoast.util.SweetToastUtil

private const val CONTENT_ANIMATION_DURATION = 300

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@SuppressLint("UnrememberedMutableState")
@Composable
fun TestScreen(
    navController: NavController,
    onBackPressed: () -> Unit,
    activeTimer: Boolean = true,
    userViewModel: UserViewModel,
    quesViewModel: QuesViewModel,
    entityViewModel: EntityViewModel,
    testType: String = "Course",
    testName: String = "Course001"
) {

    val TAG = "TestScreen"

    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var showSuccess by rememberSaveable { mutableStateOf(false) }
    var submitAlert by rememberSaveable { mutableStateOf(false) }
    var exitAlert by rememberSaveable { mutableStateOf(false) }

    var errorMsg by rememberSaveable { mutableStateOf("") }
    var successMsg by rememberSaveable { mutableStateOf("") }

    var totalQs by rememberSaveable { mutableIntStateOf(0) }
    var correctAns by rememberSaveable { mutableIntStateOf(0) }
    var wrongAns by rememberSaveable { mutableIntStateOf(0) }

    val testViewModel: TestViewModel = viewModel()

    var questionList by rememberSaveable {
        mutableStateOf(emptyList<Question>())
    }

    BackHandler {
        if (!testViewModel.onBackPressed()) {
            exitAlert = true
        }
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
                totalQs = questionList.size
            }
        }
    }

    val questionIndexData = testViewModel.questionIndexData ?: return

    LaunchedEffect(key1 = true) {
        testViewModel.onNextPressed()
        testViewModel.onPreviousPressed()
    }

    if (questionList.isNotEmpty()) {
        QuestionsScreen(
            questionIndexData = questionIndexData,
            isNextEnabled = testViewModel.isNextEnabled,
            activeTimer = activeTimer,
            timeInMinutes = totalQs.toLong(),
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
            onNextPressed = {
                entityViewModel.singleChoiceAns.value = ""
                entityViewModel.multiChoiceAns.clear()
                testViewModel.onNextPressed()
            },
            onSubmitPressed = {

                entityViewModel.getCorrectMarks(1).observe(lifecycleOwner) { marks ->
                    correctAns = marks
                }

                entityViewModel.getWrongMarks(0).observe(lifecycleOwner) { marks ->
                    wrongAns = marks
                }

                submitAlert = true

                Log.d(TAG, "onSubmitPressed: correct- $correctAns , wrong- $wrongAns")

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

                Log.d(TAG, ": $targetState ")

                if (targetState.question.optionE.isNotEmpty()) {
                    MultipleChoiceQues(
                        modifier = modifier,
                        entityViewModel = entityViewModel,
                        quesId = targetState.question.quesId,
                        questionTitle = targetState.question.ques,
                        directionsResourceId = R.string.select_all,
                        possibleAnswers = listOf(
                            targetState.question.optionA,
                            targetState.question.optionB,
                            targetState.question.optionC,
                            targetState.question.optionD,
                            targetState.question.optionE
                        ),
                        selectedAnswers = testViewModel
                            .multipleChoiceResponse[targetState.question.quesId]
                            ?: mutableStateListOf(),
                        correctAns = targetState.question.ans,
                        onOptionSelected = { selected, answer ->
                            testViewModel.onMultipleChoiceResponse(
                                selected = selected,
                                quesId = targetState.question.quesId,
                                answer = answer
                            )
                        }
                    )
                } else {
                    SingleChoiceQues(
                        modifier = modifier,
                        entityViewModel = entityViewModel,
                        quesId = targetState.question.quesId,
                        questionTitle = targetState.question.ques,
                        directionsResourceId = R.string.select_one,
                        possibleAnswers = listOf(
                            targetState.question.optionA,
                            targetState.question.optionB,
                            targetState.question.optionC,
                            targetState.question.optionD
                        ),
                        selectedAnswer = testViewModel
                            .singleChoiceResponse[targetState.question.quesId] ?: "",
                        correctAns = targetState.question.ans,
                        onOptionSelected = { ans ->
                            testViewModel.onSingleChoiceResponse(
                                quesId = targetState.question.quesId,
                                answer = ans
                            )
                        }
                    )
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

    if (showSuccess) {
        showSuccess = false
        SweetToastUtil.SweetSuccess(message = successMsg, padding = PaddingValues(10.dp))
    }

    if (exitAlert) {
        AlertDialog(
            openDialogCustom = mutableStateOf(exitAlert),
            dialogIcon = R.drawable.ic_warning,
            drawableTint = ErrorColor,
            title = "Warning!",
            message = "Are you sure to quit the Test?",
            onPositiveBtnPressed = {
                exitAlert = false
                onBackPressed()
            },
            onNegativeBtnPressed = {
                exitAlert = false
            },
        )
    }

    if (submitAlert) {
        TestSubmitAlertDialog(
            openDialogCustom = mutableStateOf(submitAlert),
            dialogIcon = R.drawable.ic_warning,
            drawableTint = PrimaryColor,
            title = "please check before submit..",
            message = "Total Questions",
            totalQs = totalQs.toString(),
            quesAnswered = (correctAns + wrongAns).toString(),
            skippedQues = (totalQs - (correctAns + wrongAns)).toString(),
            onPositiveBtnPressed = {
                submitAlert = false

                Log.d(TAG, "onSubmitPressed: correct- $correctAns , wrong- $wrongAns")

                val testResult = TestResult(
                    testType = testType,
                    testName = testName,
                    timeBased = activeTimer,
                    timeTaken = "15",
                    totalQs = totalQs.toString(),
                    answered = (correctAns + wrongAns).toString(),
                    skipped = (totalQs - (correctAns + wrongAns)).toString(),
                    correctAnswered = correctAns.toString(),
                    wrongAnswered = wrongAns.toString(),
                    status = if (((correctAns * 100) / totalQs) > 50.0f) STATUS_PASS else STATUS_FAILED,
                    createdAt = getCurrentDateTime().toString(DATE_TIME_FORMAT),
                    updatedAt = getCurrentDateTime().toString(DATE_TIME_FORMAT),
                )

                userViewModel.insertTestResult(testResult)

                testViewModel.onDonePressed {
                    navController.navigate(ContentNavScreen.Result.route.plus("/${testResult.toPrettyJson()}")) {
                        popUpTo(ContentNavScreen.Test.route)
                    }
                }
            },
            onNegativeBtnPressed = {
                submitAlert = false
            },
        )
    }

}