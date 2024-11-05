package com.testleaf.user.data.remote.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Noweshed on 3/11/24.
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        val accessToken = sessionManager.getAccessToken()

        if (response.code == 401 || response.code == 403) {
            val refreshToken = sessionManager.getRefreshToken()

            // Create a new request with the refreshed access token
            val newRequest = originalRequest.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $refreshToken")
                .build()

            // Retry the request with the new access token
            return chain.proceed(newRequest)
        } else {
            // Add the access token to the request header
            val authorizedRequest = originalRequest.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            return chain.proceed(authorizedRequest)
        }

    }

}