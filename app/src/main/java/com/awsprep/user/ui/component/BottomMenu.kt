package com.awsprep.user.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.awsprep.user.domain.models.BottomMenuContent
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.SecondaryColor

/**
 * Created by Md. Noweshed Akram on 14/11/23.
 */
@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    navController: NavController,
    activeHighlightColor: Color = Color.White,
    activeColor: Color = SecondaryColor,
    inactiveColor: Color = Color.Gray,
) {

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = ColorAccent,
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            )
            .padding(10.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomMenuItem(
                item = item,
                isSelected = currentRoute == item.route,
                activeHighlightColor = activeHighlightColor,
                activeColor = activeColor,
                inactiveColor = inactiveColor
            ) {
                navController.navigate(item.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}