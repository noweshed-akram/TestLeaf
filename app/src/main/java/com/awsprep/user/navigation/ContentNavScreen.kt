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
    object AllCourse : ContentNavScreen(route = "all_course_screen")
    object Chapters : ContentNavScreen(route = "chapters_screen")
    object Sections : ContentNavScreen(route = "sections_screen")
    object ReviewQues : ContentNavScreen(route = "review_ques_screen")
    object Timer : ContentNavScreen(route = "timer_screen")
    object Test : ContentNavScreen(route = "test_screen")
    object Result : ContentNavScreen(route = "result_screen")
}