package com.testleaf.user.data.remote.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by Noweshed on 12/10/24.
 */
@Keep
data class UserData(
    @SerializedName("user_details") var userDetails: UserDetails? = UserDetails(),
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("token_type") var tokenType: String? = null,
    @SerializedName("expires_in") var expiresIn: Int? = null,
    @SerializedName("expired_at") var expiredAt: String? = null
)
