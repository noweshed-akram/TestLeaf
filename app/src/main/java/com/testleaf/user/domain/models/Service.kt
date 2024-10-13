package com.testleaf.user.domain.models

import androidx.annotation.Keep

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Keep
data class Service(
    val name: String = "",
    val icon: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)
