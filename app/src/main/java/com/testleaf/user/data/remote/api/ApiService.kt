package com.testleaf.user.data.remote.api

import com.testleaf.user.domain.models.User
import com.testleaf.user.data.remote.model.response.AuthResponse
import com.google.gson.JsonObject
import com.testleaf.user.data.remote.model.response.ChapterResponse
import com.testleaf.user.data.remote.model.response.CourseResponse
import com.testleaf.user.data.remote.model.response.QuestionResponse
import com.testleaf.user.data.remote.model.response.ServiceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Noweshed on 28/9/24.
 */
interface ApiService {

    /**
     * User API
     */
    @POST("api/v1/register")
    suspend fun userRegistration(@Body jsonObject: JsonObject): Response<AuthResponse>

    @POST("api/v1/login")
    suspend fun userLogin(@Body jsonObject: JsonObject): Response<AuthResponse>

    @POST("api/v1/refresh")
    suspend fun refreshToken(): Response<AuthResponse>

    @POST("api/v1/update-profile")
    suspend fun updateProfile(@Body jsonObject: JsonObject): Response<User>

    @POST("api/v1/update-profile-image")
    suspend fun updateProfileImage(@Body jsonObject: JsonObject): Response<User>

    @POST("api/v1/logout")
    suspend fun userLogout(@Body jsonObject: JsonObject): Response<User>

    @POST("api/v1/get-profile")
    suspend fun getProfile(@Body jsonObject: JsonObject): Response<User>

    /**
     * Course APi
     */
    @GET("api/v1/course-list")
    suspend fun getCourseList(): Response<CourseResponse>

    @GET("api/v1/course-search")
    suspend fun searchCourse(@Query("search") searchValue: String): Response<CourseResponse>

    @GET("api/v1/service-list")
    suspend fun getServiceList(): Response<ServiceResponse>

    @GET("api/v1/service-search")
    suspend fun searchService(@Query("search") searchValue: String): Response<ServiceResponse>

    @GET("api/v1/chapter-list")
    suspend fun getChapterList(): Response<ChapterResponse>

    @GET("api/v1/chapter-search")
    suspend fun searchChapter(@Query("search") searchValue: String): Response<ChapterResponse>

    /**
     * Question APi
     */
    @GET("api/v1/question-list")
    suspend fun getQuestionList(): Response<QuestionResponse>


}