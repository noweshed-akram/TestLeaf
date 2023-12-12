package com.awsprep.user.ui.component

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
import com.awsprep.user.ui.theme.GreyColor
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.WhiteColor

/**
 * Created by Md. Noweshed Akram on 14/11/23.
 */
@Composable
fun SingleChoiceQues(
    questionTitle: String,
    @StringRes directionsResourceId: Int,
    possibleAnswers: List<String>,
    selectedAnswer: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionWrapper(
        questionTitle = questionTitle,
        directionsResourceId = directionsResourceId,
        modifier = modifier.selectableGroup(),
    ) {
        possibleAnswers.forEach {
            val selected = it == selectedAnswer
            RadioButtonWithImageRow(
                modifier = Modifier.padding(vertical = 8.dp),
                text = it,
                selected = selected,
                onOptionSelected = { onOptionSelected(it) }
            )
        }
    }
}

@Composable
fun RadioButtonWithImageRow(
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