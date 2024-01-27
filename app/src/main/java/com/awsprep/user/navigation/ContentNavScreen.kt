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
    val objectPathThree: String = "",
    val objectNameFour: String = "",
    val objectPathFour: String = ""
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
        objectName = "moduleType", objectPath = "/{moduleType}",
        objectNameTwo = "courseId", objectPathTwo = "/{courseId}",
        objectNameThree = "chapterId", objectPathThree = "/{chapterId}",
        objectNameFour = "sectionId", objectPathFour = "/{sectionId}",
    )

    object Test : ContentNavScreen(
        route = "test_screen",
        objectName = "activeTimer", objectPath = "/{activeTimer}"
    )

    object ReviewQues : ContentNavScreen(route = "review_ques_screen")

    object SubSets : ContentNavScreen(
        route = "sub_sets_screen",
        objectName = "setId", objectPath = "/{setId}",
        objectNameTwo = "subSetId", objectPathTwo = "/{subSetId}"
    )

    object Result : ContentNavScreen(
        route = "result_screen",
        objectName = "testResult", objectPath = "/{testResult}",
    )

    object ResultDashboard : ContentNavScreen(route = "result_dashboard_screen")
}