package com.awsprep.user.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Created by noweshedakram on 5/8/23.
 */
@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
        ?.substringBeforeLast("/")
        ?.substringBeforeLast("/")
        ?.substringBeforeLast("/")
        ?.substringBeforeLast("/")
}