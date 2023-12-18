package com.awsprep.user.ui.component

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.awsprep.user.data.local.entity.TestEntity
import com.awsprep.user.ui.theme.GreyColor
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.WhiteColor
import com.awsprep.user.viewmodel.EntityViewModel

/**
 * Created by Md. Noweshed Akram on 14/11/23.
 */
@Composable
fun SingleChoiceQues(
    modifier: Modifier = Modifier,
    entityViewModel: EntityViewModel,
    quesId: String,
    questionTitle: String,
    @StringRes directionsResourceId: Int,
    possibleAnswers: List<String>,
    selectedAnswer: String,
    correctAns: List<String>,
    onOptionSelected: (String) -> Unit
) {

    /*var userAns = ""
    entityViewModel.getSelectedAns(quesId).let {
        userAns = entityViewModel.selectedAns.value ?: ""
    }

    var userSelectedAns = ""
    if (userAns.isNotEmpty()) {
        if (userAns.lowercase() == "a") {
            userSelectedAns = possibleAnswers[0]
        } else if (userAns.lowercase() == "b") {
            userSelectedAns = possibleAnswers[1]
        } else if (userAns.lowercase() == "c") {
            userSelectedAns = possibleAnswers[2]
        } else if (userAns.lowercase() == "d") {
            userSelectedAns = possibleAnswers[3]
        }
    }*/

    QuestionWrapper(
        questionTitle = questionTitle,
        directionsResourceId = directionsResourceId,
        modifier = modifier.selectableGroup(),
    ) {
        possibleAnswers.forEachIndexed { index, it ->
            val selected = it == selectedAnswer
            RadioButtonWithRow(
                modifier = Modifier.padding(vertical = 8.dp),
                entityViewModel = entityViewModel,
                rowIndex = index,
                quesId = quesId,
                text = it,
                selected = selected,
                correctAns = correctAns,
                onOptionSelected = { onOptionSelected(it) }
            )
        }
    }
}

@Composable
fun RadioButtonWithRow(
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
                entityViewModel.singleChoiceAns.value = "a"
            }

            1 -> {
                entityViewModel.singleChoiceAns.value = "b"
            }

            2 -> {
                entityViewModel.singleChoiceAns.value = "c"
            }

            3 -> {
                entityViewModel.singleChoiceAns.value = "d"
            }
        }

        entityViewModel.insertTestData(
            TestEntity(
                quesId = quesId,
                correctAnswers = correctAns[0].lowercase(),
                selectedAnswers = entityViewModel.singleChoiceAns.value.lowercase(),
                marks = if (entityViewModel.singleChoiceAns.value == correctAns[0].lowercase()) 1 else 0
            )
        )

        Log.d(
            "SelectAnswers",
            "RadioButtonWithRow: $quesId ${entityViewModel.singleChoiceAns.value}"
        )
    }

    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (selected) {
            SecondaryColor
        } else {
            MaterialTheme.colorScheme.surface
        },
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) {
                SecondaryColor
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .selectable(
                selected,
                onClick = onOptionSelected,
                role = Role.RadioButton
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected,
                onClick = {
                    onOptionSelected()
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = WhiteColor,
                    unselectedColor = GreyColor
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