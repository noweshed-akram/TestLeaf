package com.awsprep.user.navigation

/**
 * Created by noweshedakram on 16/8/23.
 */
sealed class BottomNavScreen(
    val route: String
) {
    object Assessment : BottomNavScreen(route = "assessment_screen")
    object Practices : BottomNavScreen(route = "practices_screen")
    object Acronyms : BottomNavScreen(route = "acronyms_screen")
    object Definition : BottomNavScreen(route = "definition_screen")
    object MyProfile : BottomNavScreen(route = "my_profile_screen")
}