package com.awsprep.user.ui.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.awsprep.user.R
import com.awsprep.user.domain.models.Question
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.ErrorColor
import com.awsprep.user.ui.theme.GreyColor
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.Typography

/**
 * Created by Md. Noweshed Akram on 25/11/23.
 */
@SuppressLint("UnrememberedMutableState")
@Composable
fun ReviewQuesItem(
    quesNo: Int, question: Question, onQuestionDelete: (quesId: String) -> Unit
) {

    var showAlert by rememberSaveable { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically
        ) {

            Column(modifier = Modifier.weight(1.0f)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ChipItemView("Review Q$quesNo")

                    IconButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = {
                            showAlert = true
                        },
                        enabled = true,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_trash),
                            contentDescription = "delete",
                            tint = ErrorColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = question.ques.trim(), color = Color.Black, style = Typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (question.optionE.length > 1) {
                    QuesCheckboxOptions(question = question)
                } else {
                    QuesRadioOptions(question = question)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(
                            color = ColorAccent,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = PrimaryColor.copy(.2f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            text = "Correct Ans: ",
                            color = PrimaryColor,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        for (ans in question.ans) {
                            Text(
                                text = "$ans  ",
                                color = PrimaryColor,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp
                        ),
                        text = question.explain.trim(),
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            color = GreyColor.copy(.2f),
                            shape = CircleShape
                        )
                )

            }

        }
    }

    if (showAlert) {
        AlertDialog(
            openDialogCustom = mutableStateOf(showAlert),
            dialogIcon = R.drawable.ic_warning,
            drawableTint = ErrorColor,
            title = "Warning!",
            message = "Are you confirm to remove this question from the review section?",
            onPositiveBtnPressed = {
                showAlert = false
                onQuestionDelete(question.quesId)
            },
            onNegativeBtnPressed = {
                showAlert = false
            },
        )
    }

}

@Composable
fun QuesCheckboxOptions(
    question: Question
) {
    val checkBoxOptions = listOf(
        question.optionA, question.optionB, question.optionC, question.optionD, question.optionE
    )

    val selectedAnswers: MutableList<String> = mutableListOf()

    for (ans in question.ans) {
        if (ans.lowercase() == "a") {
            selectedAnswers += question.optionA
        } else if (ans.lowercase() == "b") {
            selectedAnswers += question.optionB
        } else if (ans.lowercase() == "c") {
            selectedAnswers += question.optionC
        } else if (ans.lowercase() == "d") {
            selectedAnswers += question.optionD
        } else if (ans.lowercase() == "e") {
            selectedAnswers += question.optionE
        }

        Log.d("QuesCheckboxOptions: ", selectedAnswers.toString())
    }

    checkBoxOptions.forEach { text ->

        val selected = selectedAnswers.contains(text)

        CheckboxRow(modifier = Modifier.padding(vertical = 8.dp),
            text = text,
            selected = selected,
            onOptionSelected = { })
    }
}

@Composable
fun QuesRadioOptions(
    question: Question
) {
    val radioOptions = listOf(
        question.optionA, question.optionB, question.optionC, question.optionD
    )

    var correctAns = ""

    if (question.ans[0].lowercase() == "a") {
        correctAns = question.optionA
    } else if (question.ans[0].lowercase() == "b") {
        correctAns = question.optionB
    } else if (question.ans[0].lowercase() == "c") {
        correctAns = question.optionC
    } else if (question.ans[0].lowercase() == "d") {
        correctAns = question.optionD
    }

    val (selectedOption, onOptionSelected) = rememberSaveable { mutableStateOf(correctAns) }

    radioOptions.forEach { text ->

        val selected = text == selectedOption

        RadioButtonWithImageRow(modifier = Modifier.padding(vertical = 8.dp),
            text = text,
            selected = selected,
            onOptionSelected = { })

    }
}