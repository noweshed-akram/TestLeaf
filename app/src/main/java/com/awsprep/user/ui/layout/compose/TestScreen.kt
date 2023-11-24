package com.awsprep.user.ui.layout.compose

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.awsprep.user.R
import com.awsprep.user.ui.component.MultipleChoiceQues
import com.awsprep.user.ui.component.SingleChoiceQues
import com.awsprep.user.ui.component.getTransitionDirection
import com.awsprep.user.viewmodel.QuesViewModel

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

    val TAG = "TestScreen"

    val questionIndexData = quesViewModel.questionIndexData ?: return

    QuestionsScreen(
        questionIndexData = questionIndexData,
        isNextEnabled = quesViewModel.isNextEnabled,
        onBackPressed = onBackPressed,
        onPreviousPressed = { quesViewModel.onPreviousPressed() },
        onNextPressed = { quesViewModel.onNextPressed() },
        onSubmitPressed = { quesViewModel.onDonePressed(onSubmitAnswers) }
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

            Log.d(TAG, "TestScreen: " + targetState.toString())

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
                    selectedAnswers = quesViewModel.multipleChoiceResponse,
                    onOptionSelected = quesViewModel::onMultipleChoiceResponse,
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
                    selectedAnswer = quesViewModel.singleChoiceResponse,
                    onOptionSelected = quesViewModel::onSingleChoiceResponse,
                    modifier = modifier,
                )
            }

        }
    }

}