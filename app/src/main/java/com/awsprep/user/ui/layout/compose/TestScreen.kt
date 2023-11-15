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
import com.awsprep.user.R
import com.awsprep.user.domain.models.Question
import com.awsprep.user.ui.component.MultipleChoiceQuestion
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.component.SingleChoiceQuestion
import com.awsprep.user.ui.component.getTransitionDirection
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.QuestionType
import com.talhafaki.composablesweettoast.util.SweetToastUtil

private const val CONTENT_ANIMATION_DURATION = 300

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun TestScreen(
    onBackPressed: () -> Unit,
    onSubmitAnswers: () -> Unit,
    quesViewModel: QuesViewModel
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    val screenData = quesViewModel.screenData ?: return

    var questionList by rememberSaveable {
        mutableStateOf(emptyList<Question>())
    }

    LaunchedEffect(key1 = true) {
        quesViewModel.getQuestions(
            "gAIzFo3oMkeA5vtMdtHd",
            "SPMy3zbDGh1grrxSE242",
            "L0HhAjw2STqk8gvCYhbB"
        )

        quesViewModel.questionData.collect {
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
            it.data?.let {
                showProgress = false
                questionList = it as List<Question>
            }
        }
    }

    QuestionsScreen(
        screenData = screenData,
        isNextEnabled = quesViewModel.isNextEnabled,
        onBackPressed = onBackPressed,
        onPreviousPressed = { quesViewModel.onPreviousPressed() },
        onNextPressed = { quesViewModel.onNextPressed() },
        onDonePressed = { quesViewModel.onDonePressed(onSubmitAnswers) }
    ) { paddingValues ->

        val modifier = Modifier.padding(paddingValues)

        AnimatedContent(
            targetState = screenData,
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
            label = "screenDataAnimation"
        ) { targetState ->


            for (question in questionList) {

                when (targetState.questionType) {

                    QuestionType.SINGLE_CHOICE -> {
                        SingleChoiceQuestion(
                            questionTitle = question.ques,
                            directionsResourceId = R.string.select_one,
                            possibleAnswers = listOf(
                                question.optionA,
                                question.optionB,
                                question.optionC,
                                question.optionD
                            ),
                            selectedAnswer = quesViewModel.singleChoiceResponse,
                            onOptionSelected = quesViewModel::onSingleChoiceResponse,
                            modifier = modifier,
                        )
                    }

                    QuestionType.MULTIPLE_CHOICE -> {
                        MultipleChoiceQuestion(
                            questionTitle = question.ques,
                            directionsResourceId = R.string.select_all,
                            possibleAnswers = listOf(
                                question.optionA,
                                question.optionB,
                                question.optionC,
                                question.optionD,
                                question.optionE
                            ),
                            selectedAnswers = quesViewModel.multipleChoiceResponse,
                            onOptionSelected = quesViewModel::onMultipleChoiceResponse,
                            modifier = modifier,
                        )
                    }

                    QuestionType.SINGLE_CHOICE -> {
                        SingleChoiceQuestion(
                            questionTitle = question.ques,
                            directionsResourceId = R.string.select_one,
                            possibleAnswers = listOf(
                                question.optionA,
                                question.optionB,
                                question.optionC,
                                question.optionD
                            ),
                            selectedAnswer = quesViewModel.singleChoiceResponse,
                            onOptionSelected = quesViewModel::onSingleChoiceResponse,
                            modifier = modifier,
                        )
                    }

                }
            }

        }
    }

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 10.dp, vertical = 10.dp)
//    ) {
//
//        SwipeRefresh(
//            state = rememberSwipeRefreshState(isRefreshing = false),
//            onRefresh = { }) {
//            LazyRow(
//                modifier = Modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                items(
//                    items = questionList
//                ) { question ->
//                    QuestionItem(question = question)
//                }
//            }
//        }
//
//    }

    if (showProgress) {
        ProgressBar()
    }

    if (showError) {
        showError = false
        SweetToastUtil.SweetError(message = errorMsg, padding = PaddingValues(10.dp))
    }

}