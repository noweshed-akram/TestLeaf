package com.testleaf.user.domain.models

import androidx.annotation.Keep

/**
 * Created by Md. Noweshed Akram on 25/11/23.
 */
@Keep
data class Feedback(
    val userId: String? = null,
    val questionId: String? = null,
    val feedback: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
