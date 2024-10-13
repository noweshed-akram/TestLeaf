package com.testleaf.user.ui.layout.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testleaf.user.domain.models.ExamMetaData
import com.testleaf.user.domain.models.TestResult
import com.testleaf.user.ui.component.PrimaryButton
import com.testleaf.user.ui.theme.GreyColor
import com.testleaf.user.ui.theme.PrimaryColor
import com.testleaf.user.ui.theme.SecondaryColor
import com.testleaf.user.ui.theme.WhiteColor
import com.testleaf.user.viewmodel.UserViewModel

/**
 * Created by Md. Noweshed Akram on 11/15/2023.
 */
@Composable
fun ResultScreen(
    userViewModel: UserViewModel,
    testResult: TestResult,
    examMetaData: ExamMetaData,
    onBackBtnClick: () -> Unit,
    onHomeBtnClick: () -> Unit,
    onRetakeBtnClick: (ExamMetaData) -> Unit,
    onCheckAnswersBtnClick: (ExamMetaData) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 16.dp,
                    spotColor = Color.White,
                    ambientColor = Color.White
                )
                .fillMaxWidth()
                .wrapContentHeight()
                .background(SecondaryColor, RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .background(PrimaryColor, CircleShape)
                        .height(72.dp)
                        .width(72.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = testResult.correctAnswered,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        )

                        Text(
                            text = "of ${testResult.totalQs}",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Congratulations!",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(400),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You’ve scored ${testResult.correctAnswered} points",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(700),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (testResult.timeBased) {
                    Box(
                        modifier = Modifier
                            .background(
                                Color(0xFF4C8A4D),
                                RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                            )
                            .fillMaxWidth()
                            .height(36.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "You took ${testResult.timeTaken} Min to complete the test",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(400),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {

            PrimaryButton(
                modifier = Modifier.weight(1.0f),
                onClick = {
                    onRetakeBtnClick(examMetaData)
                },
                buttonText = "Retake",
                backgroundColor = WhiteColor,
                fontColor = PrimaryColor,
                borderStrokeColor = PrimaryColor
            )

            Spacer(modifier = Modifier.width(24.dp))

            PrimaryButton(
                modifier = Modifier.weight(1.0f),
                onClick = {
                    onCheckAnswersBtnClick(examMetaData)
                },
                buttonText = "Check Answers",
                backgroundColor = WhiteColor,
                fontColor = PrimaryColor,
                borderStrokeColor = PrimaryColor
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        StatusGridComposable(
            testResult.correctAnswered,
            testResult.wrongAnswered,
            testResult.skipped,
            ((testResult.correctAnswered.toInt() * 100) / testResult.totalQs.toInt()).toString() + "%"
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            onClick = {
                onHomeBtnClick()
            },
            buttonText = "Home",
            backgroundColor = GreyColor,
            fontColor = WhiteColor,
            borderStrokeColor = GreyColor
        )

    }

    BackHandler {
        onBackBtnClick()
    }

}

@Composable
fun StatusGridComposable(
    correctAns: String,
    incorrectAns: String,
    skipped: String,
    completion: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = GreyColor.copy(.4f),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .fillMaxWidth()
            .height(180.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 8.dp)
            )
    ) {

        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = GreyColor.copy(alpha = .3f))
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 8.dp)
                .background(color = GreyColor.copy(alpha = .3f))
        )

        Row {
            Column(
                modifier = Modifier.weight(1.0f)
            ) {
                StatusTextComposable(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1.0f),
                    status = "Correct Answer",
                    progress = correctAns
                )

                StatusTextComposable(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1.0f),
                    status = "Skipped",
                    progress = skipped
                )

            }

            Column(
                modifier = Modifier.weight(1.0f)
            ) {

                StatusTextComposable(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1.0f),
                    status = "Incorrect Answer",
                    progress = incorrectAns
                )

                StatusTextComposable(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1.0f),
                    status = "Completion",
                    progress = completion
                )
            }

        }
    }
}

@Composable
fun StatusTextComposable(modifier: Modifier, status: String, progress: String) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = status,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = GreyColor,
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = progress,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                color = PrimaryColor,
                textAlign = TextAlign.Center,
            )
        )
    }
}
