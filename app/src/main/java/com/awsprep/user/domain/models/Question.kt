package com.awsprep.user.domain.models

import androidx.annotation.Keep

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Keep
data class Question(
    var quesId: String = "",
    val ques: String = "",
    val ans: List<String> = emptyList(),
    val optionA: String = "",
    val optionB: String = "",
    val optionC: String = "",
    val optionD: String = "",
    val optionE: String = "",
    val explain: String = ""
)
