package com.testleaf.user.data.remote.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by Noweshed on 30/10/24.
 */
@Keep
data class QuestionData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("question") var question: String? = null,
    @SerializedName("option_a") var optionA: String? = null,
    @SerializedName("option_b") var optionB: String? = null,
    @SerializedName("option_c") var optionC: String? = null,
    @SerializedName("option_d") var optionD: String? = null,
    @SerializedName("option_e") var optionE: String? = null,
    @SerializedName("course_id") var courseId: Int? = null,
    @SerializedName("chapter_id") var chapterId: Int? = null,
    @SerializedName("section_id") var sectionId: Int? = null,
    @SerializedName("explain") var explain: String? = null,
    @SerializedName("difficulty_level") var difficultyLevel: Int? = null,
    @SerializedName("is_rapid") var isRapid: Int? = null,
    @SerializedName("answer") var answer: ArrayList<String> = arrayListOf(),
    @SerializedName("is_added_practice") var isAddedPractice: Int? = null,
    @SerializedName("is_added_random") var isAddedRandom: Int? = null,
    @SerializedName("is_added_set") var isAddedSet: Int? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("deleted_at") var deletedAt: String? = null,
    @SerializedName("chapter") var chapter: Chapter? = Chapter(),
    @SerializedName("course") var course: Course? = Course(),
    @SerializedName("services") var services: ArrayList<String> = arrayListOf(),
    @SerializedName("tags") var tags: ArrayList<String> = arrayListOf()
)

data class Chapter(
    @SerializedName("chapter_name") var chapterName: String? = null
)

data class Course(
    @SerializedName("course_name") var courseName: String? = null
)