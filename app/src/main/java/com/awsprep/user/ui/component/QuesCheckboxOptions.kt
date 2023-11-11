package com.awsprep.user.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awsprep.user.domain.models.Question
import com.awsprep.user.ui.theme.Typography

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
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
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Text(
                text = text.trim(),
                style = Typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

}