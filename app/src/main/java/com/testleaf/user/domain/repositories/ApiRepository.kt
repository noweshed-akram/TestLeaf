package com.testleaf.user.domain.repositories

import com.google.gson.JsonObject
import com.testleaf.user.data.remote.model.response.AuthResponse
import com.testleaf.user.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

/**
 * Created by Noweshed on 12/10/24.
 */
interface ApiRepository {

    /**
     * User API
     */
    suspend fun userRegistration(@Body jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun userLogin(@Body jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun updateProfile(@Body jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun updateProfileImage(@Body jsonObject: JsonObject): Flow<Resource<AuthResponse>>

    suspend fun userLogout(): Flow<Resource<AuthResponse>>

    suspend fun getProfile(): Flow<Resource<AuthResponse>>

}