package com.awsprep.user.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.awsprep.user.ui.theme.PrimaryColorDark
import com.awsprep.user.ui.theme.Typography

@Composable
fun HomeTopView(
    userName: String = "",
    onItemClick: () -> Unit
) {

    Box(modifier = Modifier.background(color = PrimaryColorDark)) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .clickable { onItemClick() }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_menubar),
                    contentDescription = "menubar",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Row(
                modifier = Modifier.weight(1.0f)
            ) {

                Text(text = "Welcome,", color = Color.White, style = Typography.titleMedium)

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = userName,
                    color = Color.White,
                    style = Typography.titleMedium
                )

            }

            Box(
                modifier = Modifier
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