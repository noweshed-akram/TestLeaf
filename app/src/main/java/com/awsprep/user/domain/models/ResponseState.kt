package com.awsprep.user.domain.models

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
data class ResponseState(
    val data: List<Any>? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
