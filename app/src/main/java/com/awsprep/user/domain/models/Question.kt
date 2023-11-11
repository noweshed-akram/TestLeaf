package com.awsprep.user.domain.models

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
data class Question(
    val ques: String = "",
    val ans: Array<String> = emptyArray(),
    val optionA: String = "",
    val optionB: String = "",
    val optionC: String = "",
    val optionD: String = "",
    val optionE: String = "",
    val explain: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)
