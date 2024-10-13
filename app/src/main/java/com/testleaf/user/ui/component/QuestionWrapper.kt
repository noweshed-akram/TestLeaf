package com.testleaf.user.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.testleaf.user.ui.theme.ColorAccent
import com.testleaf.user.ui.theme.TextColor

/**
 * Created by Md. Noweshed Akram on 14/11/23.
 */
@Composable
fun QuestionWrapper(
    questionTitle: String,
    modifier: Modifier = Modifier,
    @StringRes directionsResourceId: Int? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(Modifier.height(8.dp))

        QuestionTitle(questionTitle)

        directionsResourceId?.let {
            Spacer(Modifier.height(16.dp))
            QuestionDirections(it)
        }

        Spacer(Modifier.height(16.dp))

        content()
    }
}

@Composable
private fun QuestionTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .width(IntrinsicSize.Max)
            .background(
                color = ColorAccent,
                shape = MaterialTheme.shapes.small
            )
            .padding(vertical = 16.dp, horizontal = 16.dp),
        text = title.trim(),
        style = MaterialTheme.typography.titleMedium,
        color = TextColor,
        textAlign = TextAlign.Justify
    )
}

@Composable
private fun QuestionDirections(
    @StringRes directionsResourceId: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        text = stringResource(id = directionsResourceId),
        color = MaterialTheme.colorScheme.onSurface
            .copy(alpha = 0.87f),
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Justify
    )
}
