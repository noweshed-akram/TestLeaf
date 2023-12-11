package com.awsprep.user.navigation

/**
 * Created by noweshedakram on 16/8/23.
 */
sealed class ContentNavScreen(
    val route: String,
    val objectName: String = "",
    val objectPath: String = "",
    val objectNameTwo: String = "",
    val objectPathTwo: String = "",
    val objectNameThree: String = "",
    val objectPathThree: String = ""
) {
    object EditProfile : ContentNavScreen(route = "edit_profile_screen")
    object Notification : ContentNavScreen(route = "notification_screen")
    object AllCourse : ContentNavScreen(route = "all_course_screen")
    object Chapters : ContentNavScreen(
        route = "chapters_screen",
        objectName = "courseId", objectPath = "/{courseId}"
    )

    object Sections : ContentNavScreen(
        route = "sections_screen",
        objectName = "courseId", objectPath = "/{courseId}",
        objectNameTwo = "chapterId", objectPathTwo = "/{chapterId}",
    )

    object Timer : ContentNavScreen(
        route = "timer_screen",
        objectName = "courseId", objectPath = "/{courseId}",
        objectNameTwo = "chapterId", objectPathTwo = "/{chapterId}",
        objectNameThree = "sectionId", objectPathThree = "/{sectionId}",
    )

    object Test : ContentNavScreen(
        route = "test_screen",
        objectName = "activeTimer", objectPath = "/{activeTimer}",
        objectNameTwo = "quesList", objectPathTwo = "/{quesList}",
    )

    object ReviewQues : ContentNavScreen(route = "review_ques_screen")
    object RandomSets : ContentNavScreen(route = "random_sets_screen")
    object PracticeSets : ContentNavScreen(route = "practice_sets_screen")
    object Result : ContentNavScreen(route = "result_screen")
    object ResultDashboard : ContentNavScreen(route = "result_dashboard_screen")
}