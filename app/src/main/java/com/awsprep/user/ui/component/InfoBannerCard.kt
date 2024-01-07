package com.awsprep.user.ui.component

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awsprep.user.R
import com.awsprep.user.ui.theme.GreyColor
import com.awsprep.user.ui.theme.SecondaryColorLight
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.WhiteColor

/**
 * Created by Md. Noweshed Akram on 21/11/23.
 */
@Composable
fun InfoBannerCard(
    @DrawableRes icon: Int,
    titleText: String,
    infoText: String,
    strokeColor: Color = StrokeColor,
    bgColor: Color = WhiteColor,
    onCardClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = strokeColor,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .fillMaxWidth()
            .height(225.dp)
            .background(color = bgColor, shape = RoundedCornerShape(size = 8.dp))
            .padding(8.dp)
            .clickable {
                onCardClick()
            }
    )
    {

        Image(
            imageVector = ImageVector.vectorResource(id = icon), contentDescription = "Stopwatch",
            modifier = Modifier
                .background(color = SecondaryColorLight, shape = CircleShape)
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = titleText,
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight(600),
                color = Color.Black,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = infoText,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(400),
                color = GreyColor,
            ),
            textAlign = TextAlign.Center
        )

    }
}

@Preview(name = "Sign in light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Sign in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TimerInfoCardPreview() {
    InfoBannerCard(
        icon = R.drawable.ic_stopwatch_off,
        titleText = "Time Base",
        infoText = "60 Minutes | 60 Questions",
        onCardClick = {}
    )
}