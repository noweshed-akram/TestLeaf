package com.testleaf.user.domain.models

import androidx.annotation.Keep

/**
 * Created by Md. Noweshed Akram on 20/11/23.
 */
@Keep
data class TestResult(
    val examType: String = "",
    val examName: String = "",
    val timeBased: Boolean = false,
    val timeTaken: String = "",
    val totalQs: String = "",
    val answered: String = "",
    val skipped: String = "",
    val correctAnswered: String = "",
    val wrongAnswered: String = "",
    val status: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)
