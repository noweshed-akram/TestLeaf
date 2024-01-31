package com.awsprep.user.domain.models

/**
 * Created by Md. Noweshed Akram on 1/31/2024.
 */
data class ExamMetaData(
    val examName: String? = "",
    val examType: String? = "",
    val courseId: String? = "",
    val chapterId: String? = "",
    val sectionId: String? = "",
    val setId: String? = "",
    val subsetId: String? = ""
)