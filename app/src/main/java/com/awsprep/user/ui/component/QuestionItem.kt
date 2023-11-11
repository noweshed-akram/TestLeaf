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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.awsprep.user.domain.models.Question
import com.awsprep.user.ui.theme.PrimaryColorLight

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun QuestionItem(
    question: Question
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                PrimaryColorLight,
                RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
            .clickable {}
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1.0f)) {

                Text(
                    text = question.ques.trim(),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(5.dp))

                if (question.optionE.length > 1) {
                    QuesCheckboxOptions(question = question)
                } else {
                    QuesRadioOptions(question = question)
                }

                Spacer(modifier = Modifier.height(5.dp))

//                Text(
//                    text = question.ans[0],
//                    color = Color.Black
//                )
            }

        }
    }
}