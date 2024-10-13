package com.testleaf.user.domain.models

/**
 * Created by noweshedakram on 17/7/23.
 */
data class UserState(
    val data: User? = null,
    val error: String = "",
    val isLoading: Boolean = false
)