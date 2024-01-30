package com.awsprep.user.ui.layout.compose

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.awsprep.user.R
import com.awsprep.user.domain.models.QuestionIndexData
import com.awsprep.user.ui.component.AlertDialog
import com.awsprep.user.ui.component.CountDownTimer
import com.awsprep.user.ui.component.PrimaryButton
import com.awsprep.user.ui.theme.ErrorColor
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.ui.theme.WhiteColor
import kotlinx.coroutines.launch

/**
 * Created by Md. Noweshed Akram on 14/11/23.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuestionsScreen(
    questionIndexData: QuestionIndexData,
    isNextEnabled: Boolean,
    activeTimer: Boolean = true,
    timeInMinutes: Long = 0,
    onBackPressed: () -> Unit,
    onClickToAddReviewQs: () -> Unit,
    onFeedbackSend: (feedback: String) -> Unit,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onSubmitPressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    var feedbackMsg by rememberSaveable { mutableStateOf("") }
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Please share your feedback", style = Typography.titleMedium)

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(
                            color = WhiteColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            border = BorderStroke(1.dp, color = StrokeColor),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    placeholder = {
                        Text(text = "write us your feedback...")
                    },
                    value = feedbackMsg,
                    onValueChange = { value ->
                        feedbackMsg = value
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = StrokeColor,
                        cursorColor = SecondaryColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                PrimaryButton(
                    onClick = {
                        if (feedbackMsg.isNotEmpty()) {
                            coroutineScope.launch {
                                if (modalSheetState.isVisible)
                                    modalSheetState.hide()

                                onFeedbackSend(feedbackMsg)
                            }
                        }
                    },
                    buttonText = "Send"
                )

            }
        }) {
        Scaffold(
            topBar = {
                QuestionTopAppBar(
                    onBackPressed = onBackPressed,
                    onClickToAddReviewQs = onClickToAddReviewQs,
                    onClickFeedback = {
                        coroutineScope.launch {
                            if (modalSheetState.isVisible)
                                modalSheetState.hide()
                            else
                                modalSheetState.show() //(ModalBottomSheetValue.Expanded)
                        }
                    },
                    activeTimer = activeTimer,
                    timeInMinutes = timeInMinutes,
                    questionIndex = questionIndexData.questionIndex,
                    totalQuestionsCount = questionIndexData.questionCount
                )
            },
            content = content,
            bottomBar = {
                QuestionBottomBar(
                    shouldShowPreviousButton = questionIndexData.shouldShowPreviousButton,
                    shouldShowSubmitButton = questionIndexData.shouldShowSubmitButton,
                    isNextButtonEnabled = isNextEnabled,
                    onPreviousPressed = onPreviousPressed,
                    onNextPressed = onNextPressed,
                    onSubmitPressed = onSubmitPressed
                )
            }
        )
    }

}

@Composable
private fun TopAppBarTitle(
    questionIndex: Int,
    totalQuestionsCount: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = "Q" + (questionIndex + 1).toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
        Text(
//            text = stringResource(R.string.question_count, totalQuestionsCount),
            text = " of Q$totalQuestionsCount",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTopAppBar(
    onBackPressed: () -> Unit,
    onClickToAddReviewQs: () -> Unit,
    onClickFeedback: () -> Unit,
    activeTimer: Boolean = false,
    timeInMinutes: Long = 0,
    questionIndex: Int,
    totalQuestionsCount: Int
) {

    var showAlert by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {

        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        showAlert = true
                    },
                    enabled = true,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "back",
                        tint = PrimaryColor
                    )
                }
            },
            title = {
                TopAppBarTitle(
                    questionIndex = questionIndex,
                    totalQuestionsCount = totalQuestionsCount
                )
            },
            actions = {

                IconButton(
                    onClick = {
                        onClickToAddReviewQs()
                    },
                    modifier = Modifier.padding(2.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_add_to_review),
                        contentDescription = "add_to_review_qs",
                        tint = SecondaryColor
                    )
                }

                // TODO recheck question later
//                IconButton(
//                    onClick = {
//
//                    }
//                ) {
//                    Icon(
//                        Icons.Outlined.RotateLeft,
//                        contentDescription = "recheck",
//                        tint = SecondaryColor
//                    )
//                }

                IconButton(
                    onClick = {
                        onClickFeedback()
                    },
                    modifier = Modifier.padding(2.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_feedback),
                        contentDescription = "send_feedback",
                        tint = SecondaryColor
                    )
                }
            }
        )

        val animatedProgress by animateFloatAsState(
            targetValue = (questionIndex + 1) / totalQuestionsCount.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
        )
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .fillMaxWidth(),
            trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        )

        Spacer(Modifier.height(8.dp))

        if (activeTimer) {
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 20.dp)
            )
            {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "Time Remains: ",
                        style = Typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                            .copy(alpha = 0.87f),
                    )
                    CountDownTimer(timeInMillisecond = timeInMinutes * 60000) // multiply the time value by 60000
                }

            }
        }

        Spacer(Modifier.height(8.dp))

    }

    if (showAlert) {
        AlertDialog(
            openDialogCustom = mutableStateOf(showAlert),
            dialogIcon = R.drawable.ic_warning,
            drawableTint = ErrorColor,
            title = "Warning!",
            message = "Are you sure to quit the Test?",
            onPositiveBtnPressed = {
                showAlert = false
                onBackPressed()
            },
            onNegativeBtnPressed = {
                showAlert = false
            },
        )
    }
}

@Composable
fun QuestionBottomBar(
    shouldShowPreviousButton: Boolean,
    shouldShowSubmitButton: Boolean,
    isNextButtonEnabled: Boolean,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onSubmitPressed: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 7.dp,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            if (shouldShowPreviousButton) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onPreviousPressed,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Previous")
                }
                Spacer(modifier = Modifier.width(16.dp))
            }

            if (shouldShowSubmitButton) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onSubmitPressed,
                    enabled = isNextButtonEnabled,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Submit")
                }
            } else {
                if (isNextButtonEnabled) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        onClick = onNextPressed,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Next")
                    }
                } else {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        onClick = onNextPressed,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Skip")
                    }
                }
            }
        }
    }
}
