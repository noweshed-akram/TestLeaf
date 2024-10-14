package com.testleaf.user.data.remote.repoimpl

import com.testleaf.user.data.remote.api.ApiService
import com.testleaf.user.data.remote.model.response.AuthResponse
import com.testleaf.user.domain.models.User
import com.testleaf.user.domain.repositories.ApiRepository
import com.testleaf.user.utils.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Noweshed on 12/10/24.
 */
@Singleton
class ApiRepoImpl @Inject constructor(
    private val apiService: ApiService
) : ApiRepository {

    private fun authResponse(response: Response<AuthResponse>): Resource<AuthResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }

    override suspend fun userRegistration(jsonObject: JsonObject): Flow<Resource<AuthResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(
                    authResponse(apiService.userRegistration(jsonObject))
                )
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection")
                )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        }

    override suspend fun userLogin(jsonObject: JsonObject): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(
                authResponse(apiService.userLogin(jsonObject))
            )
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection")
            )
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    override suspend fun updateProfile(jsonObject: JsonObject): Resource<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfileImage(jsonObject: JsonObject): Resource<User> {
        TODO("Not yet implemented")
    }

    override suspend fun userLogout(jsonObject: JsonObject): Resource<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getProfile(jsonObject: JsonObject): Resource<User> {
        TODO("Not yet implemented")
    }


}