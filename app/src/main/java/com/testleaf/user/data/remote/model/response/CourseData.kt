package com.testleaf.user.data.remote.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by Noweshed on 30/10/24.
 */
@Keep
data class CourseData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("course_name") var courseName: String? = null,
    @SerializedName("short_description") var shortDescription: String? = null,
    @SerializedName("order") var order: Int? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("course_icon") var iconData: IconData? = IconData()
)
