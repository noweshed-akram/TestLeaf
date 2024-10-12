package com.awsprep.user.domain.usecase

import com.awsprep.user.data.remote.model.response.AuthResponse
import com.awsprep.user.domain.repositories.ApiRepository
import com.awsprep.user.utils.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import javax.inject.Inject

/**
 * Created by Noweshed on 12/10/24.
 */
class ApiUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    suspend fun userRegistration(@Body jsonObject: JsonObject): Resource<AuthResponse> {
        return apiRepository.userRegistration(jsonObject)
    }

    suspend fun userLogin(@Body jsonObject: JsonObject): Flow<Resource<AuthResponse>> {
        return apiRepository.userLogin(jsonObject)
    }
}