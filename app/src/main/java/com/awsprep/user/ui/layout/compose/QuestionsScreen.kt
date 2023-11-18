package com.awsprep.user.ui.layout.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.RotateLeft
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.awsprep.user.R
import com.awsprep.user.ui.component.CountDownTimer
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.viewmodel.QuestionIndexData

/**
 * Created by Md. Noweshed Akram on 14/11/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsScreen(
    questionIndexData: QuestionIndexData,
    isNextEnabled: Boolean,
    onBackPressed: () -> Unit,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onSubmitPressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {

    Scaffold(
        topBar = {
            QuestionTopAppBar(
                onBackPressed = onBackPressed,
                questionIndex = questionIndexData.questionIndex,
                totalQuestionsCount = questionIndexData.questionCount
            )
        },
        content = content,
        bottomBar = {
            QuestionBottomBar(
                shouldShowPreviousButton = questionIndexData.shouldShowPreviousButton,
                shouldShowDoneButton = questionIndexData.shouldShowDoneButton,
                isNextButtonEnabled = isNextEnabled,
                onPreviousPressed = onPreviousPressed,
                onNextPressed = onNextPressed,
                onSubmitPressed = onSubmitPressed
            )
        }
    )
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
            text = stringResource(R.string.question_count, totalQuestionsCount),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class) // CenterAlignedTopAppBar is experimental in m3
@Composable
fun QuestionTopAppBar(
    onBackPressed: () -> Unit,
    questionIndex: Int,
    totalQuestionsCount: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        onBackPressed()
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
                    onClick = {},
                    modifier = Modifier.padding(2.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_add_to_review),
                        contentDescription = "add_to_review_qs",
                        tint = Color.Transparent
                    )
                }

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        Icons.Outlined.RotateLeft,
                        contentDescription = "recheck",
                        tint = SecondaryColor
                    )
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier.padding(2.dp)
                ) {
                    Icon(
                        Icons.Outlined.Feedback,
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
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        )

        Spacer(Modifier.height(8.dp))

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
                CountDownTimer(timeInMillisecond = 650000)
            }

        }

    }
}

@Composable
fun QuestionBottomBar(
    shouldShowPreviousButton: Boolean,
    shouldShowDoneButton: Boolean,
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
            if (shouldShowDoneButton) {
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
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onNextPressed,
                    enabled = isNextButtonEnabled,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}
