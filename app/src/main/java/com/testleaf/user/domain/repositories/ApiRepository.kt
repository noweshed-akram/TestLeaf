package com.testleaf.user.domain.repositories

import com.google.gson.JsonObject
import com.testleaf.user.data.remote.model.response.AuthResponse
import com.testleaf.user.data.remote.model.response.ChapterResponse
import com.testleaf.user.data.remote.model.response.CourseResponse
import com.testleaf.user.data.remote.model.response.QuestionResponse
import com.testleaf.user.data.remote.model.response.ServiceResponse
import com.testleaf.user.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Noweshed on 12/10/24.
 */
interface ApiRepository {

    /**
     * User API
     */
    suspend fun userRegistration(jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun userLogin(jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun refreshToken(): Flow<Resource<AuthResponse>>

    suspend fun updateProfile(jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun updateProfileImage(jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun userLogout(): Flow<Resource<AuthResponse>>

    suspend fun getProfile(): Flow<Resource<AuthResponse>>

    /**
     * Segments API
     */
    suspend fun getCourseList(): Flow<Resource<CourseResponse>>

    suspend fun getCourseList(limit: Int): Flow<Resource<CourseResponse>>

    suspend fun getServiceList(): Flow<Resource<ServiceResponse>>

    suspend fun getServiceList(limit: Int): Flow<Resource<ServiceResponse>>

    suspend fun getChapterList(): Flow<Resource<ChapterResponse>>

    suspend fun getChapterList(limit: Int): Flow<Resource<ChapterResponse>>

    /**
     * Question API
     */
    suspend fun getQuestionList(limit: Int): Flow<Resource<QuestionResponse>>

    suspend fun getQuestionByCourse(
        courseId: Int,
        limit: Int
    ): Flow<Resource<QuestionResponse>>

    suspend fun getQuestionByChapter(
        chapterId: Int,
        limit: Int
    ): Flow<Resource<QuestionResponse>>

    suspend fun getQuestionBySection(
        sectionId: Int,
        limit: Int
    ): Flow<Resource<QuestionResponse>>

}