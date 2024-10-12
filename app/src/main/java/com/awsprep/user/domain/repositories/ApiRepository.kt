package com.awsprep.user.domain.repositories

import com.awsprep.user.data.remote.model.response.AuthResponse
import com.awsprep.user.domain.models.User
import com.awsprep.user.utils.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body

/**
 * Created by Noweshed on 12/10/24.
 */
interface ApiRepository {

    suspend fun userRegistration(@Body jsonObject: JsonObject): Resource<AuthResponse>

    suspend fun userLogin(@Body jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun updateProfile(@Body jsonObject: JsonObject): Resource<User>

    suspend fun updateProfileImage(@Body jsonObject: JsonObject): Resource<User>

    suspend fun userLogout(@Body jsonObject: JsonObject): Resource<User>

    suspend fun getProfile(@Body jsonObject: JsonObject): Resource<User>
}