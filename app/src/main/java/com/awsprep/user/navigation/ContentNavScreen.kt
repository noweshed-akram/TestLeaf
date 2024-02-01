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

    object Chapters : ContentNavScreen(
        route = "chapters_screen",
        objectName = "examMetaData", objectPath = "/{examMetaData}"
    )

    object Sections : ContentNavScreen(
        route = "sections_screen",
        objectName = "examMetaData", objectPath = "/{examMetaData}"
    )

    object Timer : ContentNavScreen(
        route = "timer_screen",
        objectName = "examMetaData", objectPath = "/{examMetaData}",
    )

    object Test : ContentNavScreen(
        route = "test_screen",
        objectName = "examMetaData", objectPath = "/{examMetaData}"
    )

    object ReviewQues : ContentNavScreen(route = "review_ques_screen")

    object SubSets : ContentNavScreen(
        route = "sub_sets_screen",
        objectName = "examMetaData", objectPath = "/{examMetaData}"
    )

    object Result : ContentNavScreen(
        route = "result_screen",
        objectName = "testResult", objectPath = "/{testResult}",
    )

    object ResultDashboard : ContentNavScreen(route = "result_dashboard_screen")
}