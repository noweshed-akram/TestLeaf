package com.awsprep.user.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Created by noweshedakram on 11/7/23.
 */
@Composable
fun ImageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    buttonText: String,
    backgroundColor: Color,
    fontColor: Color,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .shadow(0.dp),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = fontColor
        ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Icon(
            imageVector = imageVector,
            modifier = modifier
                .width(24.dp)
                .height(24.dp),
            contentDescription = "drawable icons"
        )
        Spacer(modifier = Modifier.padding(end = 10.dp))
        Text(
            text = buttonText,
            color = fontColor,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
    }
}