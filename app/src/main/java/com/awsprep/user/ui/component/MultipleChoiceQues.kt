package com.awsprep.user.ui.component

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.awsprep.user.data.local.entity.TestEntity
import com.awsprep.user.ui.theme.GreyColor
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.WhiteColor
import com.awsprep.user.viewmodel.EntityViewModel

/**
 * Created by noweshedakram on 11/14/23.
 */
@Composable
fun MultipleChoiceQues(
    modifier: Modifier = Modifier,
    entityViewModel: EntityViewModel,
    quesId: String,
    questionTitle: String,
    @StringRes directionsResourceId: Int,
    possibleAnswers: List<String>,
    selectedAnswers: List<String>,
    correctAns: List<String>,
    onOptionSelected: (selected: Boolean, answer: String) -> Unit
) {
    QuestionWrapper(
        modifier = modifier,
        questionTitle = questionTitle,
        directionsResourceId = directionsResourceId,
    ) {
        possibleAnswers.forEachIndexed { index, it ->

            val selected = selectedAnswers.contains(it)

            CheckboxRow(
                modifier = Modifier.padding(vertical = 8.dp),
                entityViewModel = entityViewModel,
                rowIndex = index,
                quesId = quesId,
                text = it,
                selected = selected,
                correctAns = correctAns,
                onOptionSelected = { onOptionSelected(!selected, it) })
        }
    }
}

@Composable
fun CheckboxRow(
    modifier: Modifier = Modifier,
    entityViewModel: EntityViewModel,
    rowIndex: Int,
    quesId: String = "",
    text: String,
    selected: Boolean,
    correctAns: List<String> = emptyList(),
    onOptionSelected: () -> Unit
) {

    if (selected) {

        when (rowIndex) {
            0 -> {
                if (!entityViewModel.multiChoiceAns.contains("a"))
                    entityViewModel.multiChoiceAns += "a"
            }

            1 -> {
                if (!entityViewModel.multiChoiceAns.contains("b"))
                    entityViewModel.multiChoiceAns += "b"
            }

            2 -> {
                if (!entityViewModel.multiChoiceAns.contains("c"))
                    entityViewModel.multiChoiceAns += "c"
            }

            3 -> {
                if (!entityViewModel.multiChoiceAns.contains("d"))
                    entityViewModel.multiChoiceAns += "d"
            }

            4 -> {
                if (!entityViewModel.multiChoiceAns.contains("e"))
                    entityViewModel.multiChoiceAns += "e"
            }
        }

    } else {

        when (rowIndex) {
            0 -> {
                if (entityViewModel.multiChoiceAns.contains("a"))
                    entityViewModel.multiChoiceAns -= "a"
            }

            1 -> {
                if (entityViewModel.multiChoiceAns.contains("b"))
                    entityViewModel.multiChoiceAns -= "b"
            }

            2 -> {
                if (entityViewModel.multiChoiceAns.contains("c"))
                    entityViewModel.multiChoiceAns -= "c"
            }

            3 -> {
                if (entityViewModel.multiChoiceAns.contains("d"))
                    entityViewModel.multiChoiceAns -= "d"
            }

            4 -> {
                if (entityViewModel.multiChoiceAns.contains("e"))
                    entityViewModel.multiChoiceAns -= "e"
            }
        }
    }

    var isCorrect = false

    if (correctAns.size != entityViewModel.multiChoiceAns.size) {
        isCorrect = false
    } else {
        for (ans in correctAns.sorted()) {
            for (selectAns in entityViewModel.multiChoiceAns.sorted()) {
                isCorrect = ans.lowercase() == selectAns.lowercase()
            }
        }
    }

    entityViewModel.insertTestData(
        TestEntity(
            quesId = quesId,
            correctAnswers = correctAns.sorted().toString().lowercase(),
            selectedAnswers = entityViewModel.multiChoiceAns.sorted().toString().lowercase(),
            marks = if (entityViewModel.multiChoiceAns.size < 1) -1 else if (isCorrect) 1 else 0
        )
    )

    Log.d("SelectAnswers", "CheckboxRow: $quesId ${entityViewModel.multiChoiceAns} ")

    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (selected) {
            SecondaryColor
        } else {
            MaterialTheme.colorScheme.surface
        },
        border = BorderStroke(
            width = 1.dp, color = if (selected) {
                SecondaryColor
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(onClick = onOptionSelected)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                selected,
                onCheckedChange = {
                    onOptionSelected()
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = WhiteColor,
                    uncheckedColor = GreyColor,
                    checkmarkColor = SecondaryColor
                )
            )
            Text(
                modifier = Modifier.weight(1f),
                text = text.trim(),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )
        }
    }

}