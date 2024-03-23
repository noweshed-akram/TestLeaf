package com.awsprep.user.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.awsprep.user.R
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.publicSansFamily

/**
 * Created by Md. Noweshed Akram on 14/11/23.
 */
@Composable
fun HomeTopView(
    userName: String = "",
    userImage: String = "",
    onNavigationClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileImageClick: () -> Unit
) {

    Box(
        modifier = Modifier.background(
            color = PrimaryColor,
            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
        )
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .clickable { onNavigationClick() }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_menubar),
                    contentDescription = "menubar",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                modifier = Modifier.weight(1.0f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Welcome",
                    color = Color.White,
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = userName,
                    color = Color.White,
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )

            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onNotificationClick() }
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_notification),
                    contentDescription = "notification",
                    tint = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onProfileImageClick() }
            ) {
                AsyncImage(
                    model = userImage,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_person)
                )
            }
        }
    }
}