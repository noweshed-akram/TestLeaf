package com.testleaf.user.domain.models

/**
 * Created by Md. Noweshed Akram on 1/31/2024.
 */
data class ExamMetaData(
    val examName: String? = "",
    val examType: String? = "",
    val activeTimer: Boolean? = false,
    val courseId: String? = "",
    val chapterId: String? = "",
    val sectionId: String? = "",
    val setId: String? = "",
    val setFlag: String? = "",
    val subsetId: String? = ""
)