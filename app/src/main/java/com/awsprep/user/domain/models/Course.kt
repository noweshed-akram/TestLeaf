package com.awsprep.user.domain.models

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Keep
data class Course(
    var docId: String = "",
    val name: String = "",
    val icon: String = "",
    val order: String = "",
    val is_active: Boolean = false,
//    val discussion: String = "",
//    @SerializedName("created_at")
//    val createdAt: Timestamp = Timestamp.now(),
//    @SerializedName("updated_at")
//    val updatedAt: Timestamp = Timestamp.now(),
) : Serializable