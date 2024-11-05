package com.testleaf.user.data.remote.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by Noweshed on 30/10/24.
 */
@Keep
data class IconData(
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("url-sm")
    var urlSmall: String? = null,
    @SerializedName("url-md")
    var urlMedium: String? = null
)