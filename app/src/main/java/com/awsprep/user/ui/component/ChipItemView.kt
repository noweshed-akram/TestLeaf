package com.awsprep.user.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.Typography

/**
 * Created by Md. Noweshed Akram on 9/12/23.
 */
@Composable
fun ChipItemView(
    text: String = ""
) {
    Text(
        text = text.trim(),
        style = Typography.titleSmall,
        color = PrimaryColor,
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = ColorAccent,
                shape = RoundedCornerShape(50.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Preview(name = "Sign in light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Sign in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ChipItemViewPreview() {
    ChipItemView(text = "Topic Name")
}