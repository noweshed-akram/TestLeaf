package com.awsprep.user.domain.models

/**
 * Created by Md. Noweshed Akram on 20/11/23.
 */
data class TestResult(
    val testType: String = "",
    val testName: String = "",
    val totalQs: String = "",
    val answered: String = "",
    val correctAnswered: String = "",
    val wrongAnswered: String = "",
    val status: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)
