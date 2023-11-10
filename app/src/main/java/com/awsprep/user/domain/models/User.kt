package com.awsprep.user.domain.models

/**
 * Created by noweshedakram on 14/7/23.
 */
data class User(
    val name: String = "",
    val image: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)