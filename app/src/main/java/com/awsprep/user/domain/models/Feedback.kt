package com.awsprep.user.domain.models

/**
 * Created by Md. Noweshed Akram on 25/11/23.
 */
data class Feedback(
    val userId: String? = null,
    val questionId: String? = null,
    val feedback: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
