package com.testleaf.user.data.remote.di

import android.util.Log
import com.testleaf.user.data.remote.api.TokenService
import com.testleaf.user.domain.usecase.EntityUseCase
import com.testleaf.user.utils.AppConstant
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Noweshed on 3/11/24.
 */
@Singleton
class SessionManager @Inject constructor(
    private val tokenService: TokenService,
    private val entityUseCase: EntityUseCase
) {

    val TAG = "SessionManager"
    private var refreshToken: String? = null

    fun getRefreshToken(): String {
        runBlocking {
            val response = tokenService.refreshToken()

            if (response.code() == 200) {
                refreshToken = response.body()?.data?.accessToken!!

                // Update the refreshed access token and its expiration time in the session
                entityUseCase.updateAccessToken(
                    response.body()?.data?.userDetails?.id!!,
                    response.body()?.data?.accessToken!!,
                    response.body()?.data?.expiresIn!!,
                    response.body()?.data?.expiredAt!!
                )
            } else {
                refreshToken = ""
            }
        }

        Log.d(TAG, "refreshToken: $refreshToken")
        return refreshToken!!
    }

    fun getAccessToken(): String {
        return AppConstant.AUTH_TOKEN
    }

}