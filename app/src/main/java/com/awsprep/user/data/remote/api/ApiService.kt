package com.awsprep.user.data.remote.api

import com.awsprep.user.domain.models.User
import com.awsprep.user.data.remote.model.response.AuthResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Noweshed on 28/9/24.
 */
interface ApiService {

    @POST("api/v1/register")
    suspend fun userRegistration(@Body jsonObject: JsonObject): Response<AuthResponse>

    @POST("api/v1/login")
    suspend fun userLogin(@Body jsonObject: JsonObject): Response<AuthResponse>

    @POST("api/v1/update-profile")
    suspend fun updateProfile(@Body jsonObject: JsonObject): Response<User>

    @POST("api/v1/update-profile-image")
    suspend fun updateProfileImage(@Body jsonObject: JsonObject): Response<User>

    @POST("api/v1/logout")
    suspend fun userLogout(@Body jsonObject: JsonObject): Response<User>

    @POST("api/v1/get-profile")
    suspend fun getProfile(@Body jsonObject: JsonObject): Response<User>

}