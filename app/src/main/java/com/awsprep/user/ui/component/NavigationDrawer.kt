package com.awsprep.user.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awsprep.user.domain.models.NavItem
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.Typography

/**
 * Created by Md. Noweshed Akram on 19/11/23.
 */
@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .width(250.dp)
            .background(color = SecondaryColor)
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "AWS Test Prep")
    }
}

@Composable
fun DrawerBody(
    items: List<NavItem>,
    modifier: Modifier = Modifier,
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
                    .padding(16.dp)
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