package com.awsprep.user.domain.models

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
data class ResponseState(
    val dataList: List<Any>? = null,
    val data: Any? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
