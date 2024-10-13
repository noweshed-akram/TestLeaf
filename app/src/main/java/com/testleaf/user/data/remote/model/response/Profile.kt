package com.testleaf.user.data.remote.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by Noweshed on 12/10/24.
 */
@Keep
data class Profile(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country_code") var countryCode: String? = null,
    @SerializedName("phone_number") var phoneNumber: String? = null,
    @SerializedName("birth_date") var birthDate: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("profile_avatar") var profileAvatar: String? = null
)
