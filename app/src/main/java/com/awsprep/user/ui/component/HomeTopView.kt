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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.awsprep.user.R
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.Typography

/**
 * Created by Md. Noweshed Akram on 14/11/23.
 */
@Composable
fun HomeTopView(
    userName: String = "",
    onNavigationClick: () -> Unit,
    onNotificationClick: () -> Unit
) {

    Box(modifier = Modifier.background(color = PrimaryColor)) {
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

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1.0f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Welcome", color = Color.White, style = Typography.headlineSmall)

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = userName,
                    color = Color.White,
                    style = Typography.bodyMedium
                )

            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onNotificationClick() }
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