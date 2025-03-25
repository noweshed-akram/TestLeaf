package com.testleaf.user.data.remote.repoimpl

import com.google.gson.JsonObject
import com.testleaf.user.data.remote.api.ApiService
import com.testleaf.user.data.remote.model.response.AuthResponse
import com.testleaf.user.data.remote.model.response.ChapterResponse
import com.testleaf.user.data.remote.model.response.CourseResponse
import com.testleaf.user.data.remote.model.response.QuestionResponse
import com.testleaf.user.data.remote.model.response.ServiceResponse
import com.testleaf.user.domain.repositories.ApiRepository
import com.testleaf.user.utils.Resource
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

    /**
     * User API
     */
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
                emit(authResponse(apiService.userRegistration(jsonObject)))
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
            emit(authResponse(apiService.userLogin(jsonObject)))
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

    override suspend fun refreshToken(): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(authResponse(apiService.refreshToken()))
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

    override suspend fun updateProfile(jsonObject: JsonObject): Flow<Resource<AuthResponse>> =
        flow {
            TODO("Not yet implemented")
        }

    override suspend fun updateProfileImage(jsonObject: JsonObject): Flow<Resource<AuthResponse>> =
        flow {
            TODO("Not yet implemented")
        }

    override suspend fun userLogout(): Flow<Resource<AuthResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProfile(): Flow<Resource<AuthResponse>> {
        TODO("Not yet implemented")
    }


    /**
     * Segments API
     */
    private fun courseResponse(response: Response<CourseResponse>): Resource<CourseResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }

    override suspend fun getCourseList(): Flow<Resource<CourseResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(courseResponse(apiService.getCourseList()))
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

    override suspend fun getCourseList(limit: Int): Flow<Resource<CourseResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(
                courseResponse(apiService.getCourseList(limit))
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

    private fun serviceResponse(response: Response<ServiceResponse>): Resource<ServiceResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }

    override suspend fun getServiceList(): Flow<Resource<ServiceResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(serviceResponse(apiService.getServiceList()))
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

    override suspend fun getServiceList(limit: Int): Flow<Resource<ServiceResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(serviceResponse(apiService.getServiceList(limit)))
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

    private fun chapterResponse(response: Response<ChapterResponse>): Resource<ChapterResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }

    override suspend fun getChapterList(): Flow<Resource<ChapterResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(chapterResponse(apiService.getChapterList()))
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

    override suspend fun getChapterList(limit: Int): Flow<Resource<ChapterResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(chapterResponse(apiService.getChapterList(limit)))
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


    /**
     * Question API
     */
    private fun questionResponse(response: Response<QuestionResponse>): Resource<QuestionResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }

    override suspend fun getQuestionList(limit: Int): Flow<Resource<QuestionResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(questionResponse(apiService.getQuestionList(limit)))
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

    override suspend fun getQuestionByCourse(
        courseId: Int,
        limit: Int
    ): Flow<Resource<QuestionResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(questionResponse(apiService.getQuestionByCourse(courseId, limit)))
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

    override suspend fun getQuestionByChapter(
        chapterId: Int,
        limit: Int
    ): Flow<Resource<QuestionResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(questionResponse(apiService.getQuestionByChapter(chapterId, limit)))
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

    override suspend fun getQuestionBySection(
        sectionId: Int,
        limit: Int
    ): Flow<Resource<QuestionResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(questionResponse(apiService.getQuestionBySection(sectionId, limit)))
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

}