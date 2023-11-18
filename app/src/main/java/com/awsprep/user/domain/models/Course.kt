package com.awsprep.user.domain.models

import java.io.Serializable

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
data class Course(
    val name: String = "",
    val icon: String = "",
    val order: String = "",
//    val discussion: String = "",
//    @SerializedName("created_at")
//    val createdAt: Timestamp = Timestamp.now(),
//    @SerializedName("updated_at")
//    val updatedAt: Timestamp = Timestamp.now(),
) : Serializable