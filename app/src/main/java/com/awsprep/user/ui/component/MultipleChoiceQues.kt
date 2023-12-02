package com.awsprep.user.ui.component

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
import androidx.compose.ui.unit.dp
import com.awsprep.user.ui.theme.GreyColor
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.WhiteColor

/**
 * Created by noweshedakram on 11/14/23.
 */
@Composable
fun MultipleChoiceQues(
    questionTitle: String,
    @StringRes directionsResourceId: Int,
    possibleAnswers: List<String>,
    selectedAnswers: List<String>,
    onOptionSelected: (selected: Boolean, answer: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionWrapper(
        modifier = modifier,
        questionTitle = questionTitle,
        directionsResourceId = directionsResourceId,
    ) {
        possibleAnswers.forEach {
            val selected = selectedAnswers.contains(it)
            CheckboxRow(
                modifier = Modifier.padding(vertical = 8.dp),
                text = it,
                selected = selected,
                onOptionSelected = { onOptionSelected(!selected, it) }
            )
        }
    }
}

@Composable
fun CheckboxRow(
    text: String,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            .clickable(onClick = onOptionSelected)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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
            Text(text.trim(), Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
        }
    }
}