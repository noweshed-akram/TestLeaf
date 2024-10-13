package com.testleaf.user.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.testleaf.user.R
import com.testleaf.user.domain.models.NavItem
import com.testleaf.user.ui.theme.SecondaryColor
import com.testleaf.user.ui.theme.Typography

/**
 * Created by Md. Noweshed Akram on 19/11/23.
 */
@Composable
fun DrawerHeader(
    userName: String = "",
    userEmail: String = "",
    userImage: String = "",
) {
    Box(
        modifier = Modifier
            .width(240.dp)
            .background(color = SecondaryColor)
            .padding(horizontal = 20.dp, vertical = 40.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Column() {

            AsyncImage(
                model = userImage,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_person)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = userName, style = Typography.bodyLarge)

            Text(text = userEmail, style = Typography.bodySmall)
        }
    }
}

@Composable
fun DrawerBody(
    modifier: Modifier = Modifier,
    items: List<NavItem>,
    onItemClick: (NavItem) -> Unit
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = Typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}