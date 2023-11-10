package com.awsprep.user.navigation

/**
 * Created by noweshedakram on 16/8/23.
 */
sealed class ContentNavScreen(
    val route: String,
    val objectName: String = "",
    val objectPath: String = ""
) {
    object EditProfile : ContentNavScreen(route = "edit_profile_screen")
    object Notification : ContentNavScreen(route = "notification_screen")
}