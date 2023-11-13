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
import com.awsprep.user.domain.models.Question
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.component.getTransitionDirection
import com.awsprep.user.viewmodel.QuesViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil

private const val CONTENT_ANIMATION_DURATION = 300

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun QuestionScreen(
    onSubmitAnswers: () -> Unit,
    quesViewModel: QuesViewModel
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

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
        screenData = questionList,
        isNextEnabled = quesViewModel.isNextEnabled,
        onPreviousPressed = { quesViewModel.onPreviousPressed() },
        onNextPressed = { quesViewModel.onNextPressed() },
        onDonePressed = { quesViewModel.onDonePressed(onSubmitAnswers) }
    ) { paddingValues ->

        val modifier = Modifier.padding(paddingValues)

        AnimatedContent(
            targetState = questionList,
            transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)

                val direction = getTransitionDirection(
                    initialIndex = 0,
                    targetIndex = 1,
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