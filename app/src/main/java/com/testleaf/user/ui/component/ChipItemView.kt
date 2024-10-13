package com.testleaf.user.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.testleaf.user.ui.theme.PrimaryColor
import com.testleaf.user.ui.theme.StrokeColor
import com.testleaf.user.ui.theme.Typography
import com.testleaf.user.ui.theme.WhiteColor

/**
 * Created by Md. Noweshed Akram on 9/12/23.
 */
@Composable
fun ChipItemView(
    text: String = "",
    bgColor: Color = WhiteColor,
    onChipItemClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = bgColor,
                shape = RoundedCornerShape(50.dp)
            )
            .border(width = 1.dp, shape = RoundedCornerShape(50.dp), color = StrokeColor)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                onChipItemClick()
            },
        text = text.trim(),
        style = Typography.titleSmall,
        color = PrimaryColor
    )
}

@Preview(name = "Sign in light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Sign in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ChipItemViewPreview() {
    ChipItemView(text = "Topic Name", onChipItemClick = {})
}