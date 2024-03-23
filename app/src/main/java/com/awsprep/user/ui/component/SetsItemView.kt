package com.awsprep.user.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.awsprep.user.R
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.WhiteColor
import com.awsprep.user.ui.theme.publicSansFamily

/**
 * Created by Md. Noweshed Akram on 17/11/23.
 */
@Composable
fun SetsItemView(
    modifier: Modifier = Modifier,
    @DrawableRes setsIcon: Int,
    title: String = "",
    subTitle: String = "",
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = StrokeColor)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(8.dp)
                    .clip(RoundedCornerShape(bottomStart = 8.dp, topStart = 8.dp))
                    .background(color = PrimaryColor)
            )

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(color = WhiteColor, shape = CircleShape)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("")
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                        .clip(CircleShape),
                    error = painterResource(id = setsIcon),
                    colorFilter = ColorFilter.tint(color = PrimaryColor)
                )
            }

            Column(
                modifier = Modifier.weight(1.0f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = title,
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 1
                )
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = subTitle,
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = PrimaryColor,
                    maxLines = 1
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp),
                error = painterResource(id = R.drawable.ic_arrow_forward),
                colorFilter = ColorFilter.tint(color = PrimaryColor)
            )

        }
    }
}