package com.awsprep.user.domain.models

import androidx.annotation.Keep

/**
 * Created by noweshedakram on 14/7/23.
 */
@Keep
data class User(
    val name: String = "",
    val image: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)