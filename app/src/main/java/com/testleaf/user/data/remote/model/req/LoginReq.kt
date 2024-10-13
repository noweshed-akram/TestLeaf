package com.testleaf.user.data.remote.model.req

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Noweshed on 12/10/24.
 */
@Keep
data class LoginReq(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("password")
    var password: String? = null
) : Serializable
