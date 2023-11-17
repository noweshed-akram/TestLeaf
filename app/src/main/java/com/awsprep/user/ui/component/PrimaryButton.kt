package com.awsprep.user.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.WhiteColor

/**
 * Created by Md. Noweshed Akram on 17/11/23.
 */
@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String,
    backgroundColor: Color = SecondaryColor,
    fontColor: Color = WhiteColor,
    borderStrokeColor: Color = WhiteColor,
) {
    TextButton(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = fontColor
        ),
        border = BorderStroke(1.dp, borderStrokeColor),
        onClick = { onClick() }) {
        Text(
            text = buttonText,
            color = fontColor,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
    }
}