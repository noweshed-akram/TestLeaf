package com.testleaf.user.domain.repositories

import com.testleaf.user.data.remote.model.response.AuthResponse
import com.testleaf.user.domain.models.User
import com.testleaf.user.utils.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

/**
 * Created by Noweshed on 12/10/24.
 */
interface ApiRepository {

    suspend fun userRegistration(@Body jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun userLogin(@Body jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun updateProfile(@Body jsonObject: JsonObject): Resource<User>

    suspend fun updateProfileImage(@Body jsonObject: JsonObject): Resource<User>

    suspend fun userLogout(@Body jsonObject: JsonObject): Resource<User>

    suspend fun getProfile(@Body jsonObject: JsonObject): Resource<User>
}