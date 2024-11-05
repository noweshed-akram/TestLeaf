package com.testleaf.user.domain.repositories

import com.google.gson.JsonObject
import com.testleaf.user.data.remote.model.response.AuthResponse
import com.testleaf.user.data.remote.model.response.ChapterResponse
import com.testleaf.user.data.remote.model.response.CourseResponse
import com.testleaf.user.data.remote.model.response.QuestionResponse
import com.testleaf.user.data.remote.model.response.ServiceResponse
import com.testleaf.user.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

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
     * Course APi
     */
    suspend fun getCourseList(): Flow<Resource<CourseResponse>>

    suspend fun searchCourse(searchValue: String): Flow<Resource<CourseResponse>>

    suspend fun getServiceList(): Flow<Resource<ServiceResponse>>

    suspend fun searchService(searchValue: String): Flow<Resource<ServiceResponse>>

    suspend fun getChapterList(): Flow<Resource<ChapterResponse>>

    suspend fun searchChapter(searchValue: String): Flow<Resource<ChapterResponse>>

    /**
     * Question APi
     */
    suspend fun getQuestionList(): Flow<Resource<QuestionResponse>>

}