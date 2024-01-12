package com.awsprep.user.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awsprep.user.R
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.Typography

/**
 * Created by Md. Noweshed Akram on 7/1/24.
 */
@Composable
fun NotificationListItem() {

    Column(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    ) {

        Row(
            modifier = Modifier.background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(color = ColorAccent, shape = CircleShape)
            ) {
                Image(
                    modifier = Modifier.padding(12.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_notification),
                    contentDescription = "notification_icon"
                )
            }

            Column(
                modifier = Modifier.weight(1.0f)
            ) {
                Text(text = "Welcome to Test Prep...", style = Typography.bodyLarge)

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "1 Jan 2024", style = Typography.bodySmall)
            }

        }
    }

}

@Composable
@Preview(showBackground = true)
fun NotificationListItemPreview() {
    NotificationListItem()
}