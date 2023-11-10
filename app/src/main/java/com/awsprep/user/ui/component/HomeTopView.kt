package com.awsprep.user.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.awsprep.user.R
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.ui.theme.PrimaryColorDark
import com.awsprep.user.ui.theme.PrimaryColorLight

@Composable
fun HomeTopView(
    userName: String = "",
    onItemClick: () -> Unit
) {

    Box(modifier = Modifier.background(color = PrimaryColorDark)) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1.0f)
            ) {
                Text(text = "Good Evening,", color = Color.White)
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = userName,
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable { onItemClick() }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_notification),
                    contentDescription = "notification",
                    tint = Color.White
                )
            }
        }
    }
}