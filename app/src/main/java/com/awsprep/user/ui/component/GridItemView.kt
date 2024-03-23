package com.awsprep.user.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.awsprep.user.R
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.publicSansFamily


/**
 * Created by Md. Noweshed Akram on 23/3/24.
 * noweshed@gmail.com
 */
@Composable
fun GridItemView(
    title: String = "Title",
    subTitle: String = "10+ Chapters",
    iconUrl: String = "icon_url",
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                StrokeColor,
                RoundedCornerShape(8.dp)
            )
            .clickable {
                onItemClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(color = StrokeColor, shape = CircleShape)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(iconUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(52.dp)
                        .padding(1.dp)
                        .clip(CircleShape),
                    error = painterResource(id = R.drawable.ic_error_icon)
                )
            }

            Text(
                modifier = Modifier.padding(10.dp),
                text = title,
                fontFamily = publicSansFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 2
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(color = ColorAccent)
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = subTitle,
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Black,
                    maxLines = 1
                )
            }
        }
    }
}