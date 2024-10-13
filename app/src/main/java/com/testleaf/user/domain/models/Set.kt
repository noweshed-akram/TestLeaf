package com.testleaf.user.domain.models

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by Md. Noweshed Akram on 1/21/2024.
 */
@Keep
data class Set(
    var setId: String = "",
    val name: String = "",
//    val icon: String = "",
    val order: String = "",
    val flag: String = "",
    val is_active: Boolean = false,
//    val discussion: String = "",
//    @SerializedName("created_at")
//    val createdAt: Timestamp = Timestamp.now(),
//    @SerializedName("updated_at")
//    val updatedAt: Timestamp = Timestamp.now(),
) : Serializable