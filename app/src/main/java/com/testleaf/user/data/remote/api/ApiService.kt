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
import retrofit2.http.Path
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
     * Segments API
     */
    @GET("api/v1/course-list")
    suspend fun getCourseList(): Response<CourseResponse>

    @GET("api/v1/course-list")
    suspend fun getCourseList(@Query("limit") limit: Int): Response<CourseResponse>

    @GET("api/v1/service-list")
    suspend fun getServiceList(): Response<ServiceResponse>

    @GET("api/v1/service-list")
    suspend fun getServiceList(@Query("limit") limit: Int): Response<ServiceResponse>

    @GET("api/v1/chapter-list")
    suspend fun getChapterList(): Response<ChapterResponse>

    @GET("api/v1/chapter-list")
    suspend fun getChapterList(@Query("limit") limit: Int): Response<ChapterResponse>

    /**
     * Questions API
     */
    @GET("api/v1/question-list")
    suspend fun getQuestionList(@Query("limit") limit: Int): Response<QuestionResponse>

    @GET("api/v1/question-list/{course_id}")
    suspend fun getQuestionByCourse(
        @Path("course_id") courseId: Int,
        @Query("limit") limit: Int
    ): Response<QuestionResponse>

    @GET("api/v1/question-list/course/{chapter_id}")
    suspend fun getQuestionByChapter(
        @Path("chapter_id") chapterId: Int,
        @Query("limit") limit: Int
    ): Response<QuestionResponse>

    @GET("api/v1/question-list/course/chapter/{section_id}")
    suspend fun getQuestionBySection(
        @Path("section_id") sectionId: Int,
        @Query("limit") limit: Int
    ): Response<QuestionResponse>

}