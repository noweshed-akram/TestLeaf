package com.testleaf.user.data.remote.model.req

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Noweshed on 19/10/24.
 */
@Keep
data class UpdateProfileReq(
    @SerializedName("name") var name: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("birth_date") var birthDate: String? = null,
    @SerializedName("address") var address: String? = null
) : Serializable
