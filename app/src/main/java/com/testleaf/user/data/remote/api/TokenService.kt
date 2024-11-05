package com.testleaf.user.data.remote.api

import com.testleaf.user.data.remote.model.response.AuthResponse
import retrofit2.Response
import retrofit2.http.POST

/**
 * Created by Noweshed on 5/11/24.
 */
interface TokenService {
    @POST("api/v1/refresh")
    suspend fun refreshToken(): Response<AuthResponse>
}