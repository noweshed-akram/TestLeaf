package com.awsprep.user.domain.models

/**
 * Created by Md. Noweshed Akram on 20/11/23.
 */
data class QuestionIndexData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowSubmitButton: Boolean,
    val question: Question
)
