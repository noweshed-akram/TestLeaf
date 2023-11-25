package com.awsprep.user.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.RadioButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.awsprep.user.R
import com.awsprep.user.domain.models.Question
import com.awsprep.user.ui.theme.ErrorColor
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.Typography

/**
 * Created by Md. Noweshed Akram on 25/11/23.
 */
@Composable
fun ReviewQuesItem(
    question: Question,
    onQuestionDelete: (quesId: String) -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                PrimaryColor,
                RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = CenterVertically
        ) {

            Column(modifier = Modifier.weight(1.0f)) {

                IconButton(
                    onClick = {
                        onQuestionDelete(question.quesId)
                    },
                    enabled = true,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_trash),
                        contentDescription = "delete",
                        tint = ErrorColor
                    )
                }

                Text(
                    text = question.ques,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(5.dp))

                if (question.optionE.length > 1) {
                    QuesCheckboxOptions(question = question)
                } else {
                    QuesRadioOptions(question = question)
                }

                Spacer(modifier = Modifier.height(5.dp))

                for (ans in question.ans) {
                    Text(
                        text = ans,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = question.explain,
                    color = Color.Black
                )

            }

        }
    }
}

@Composable
fun QuesCheckboxOptions(
    question: Question
) {
    val checkBoxOptions = listOf(
        question.optionA, question.optionB,
        question.optionC, question.optionD, question.optionE
    )

    checkBoxOptions.forEach { text ->

        var isChecked by rememberSaveable { mutableStateOf(false) }

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = CenterVertically
        ) {

            Checkbox(checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Text(
                text = text,
                style = Typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun QuesRadioOptions(
    question: Question
) {
    val radioOptions = listOf(
        question.optionA, question.optionB,
        question.optionC, question.optionD
    )

    val (selectedOption, onOptionSelected) = rememberSaveable { mutableStateOf("") }

    radioOptions.forEach { text ->

        Row(
            Modifier
                .fillMaxWidth()
                .selectable(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                    },
                ),
            verticalAlignment = CenterVertically
        ) {
            RadioButton(
                selected = (text == selectedOption),
                onClick = { onOptionSelected(text) }
            )
            Text(
                text = text,
                style = Typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}