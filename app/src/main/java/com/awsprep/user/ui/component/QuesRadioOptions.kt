package com.awsprep.user.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awsprep.user.domain.models.Question
import com.awsprep.user.ui.theme.Typography

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun QuesRadioOptions(
    question: Question
) {
    val radioOptions = listOf(
        question.optionA, question.optionB,
        question.optionC, question.optionD
    )

    val answers = listOf("A", "B", "C", "D")
    val selectedAns: List<String> = emptyList()
    val (selectedOption, onOptionSelected) = rememberSaveable { mutableStateOf("") }

    radioOptions.forEach { option ->

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = CenterVertically
        ) {
            RadioButton(
                selected = (option == selectedOption),
                onClick = {
                    onOptionSelected(option)
                    Log.d("Question", "QuesRadioOptions: " )
                }
            )
            Text(
                text = option.trim(),
                style = Typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}