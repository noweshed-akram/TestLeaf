package com.testleaf.user.domain.usecase

import com.google.gson.JsonObject
import com.testleaf.user.data.remote.model.response.AuthResponse
import com.testleaf.user.data.remote.model.response.ChapterResponse
import com.testleaf.user.data.remote.model.response.CourseResponse
import com.testleaf.user.data.remote.model.response.QuestionResponse
import com.testleaf.user.data.remote.model.response.ServiceResponse
import com.testleaf.user.domain.repositories.ApiRepository
import com.testleaf.user.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Noweshed on 12/10/24.
 */
class ApiUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend fun userRegistration(jsonObject: JsonObject): Flow<Resource<AuthResponse>> {
        return apiRepository.userRegistration(jsonObject)
    }

    suspend fun userLogin(jsonObject: JsonObject): Flow<Resource<AuthResponse>> {
        return apiRepository.userLogin(jsonObject)
    }

    suspend fun refreshToken(): Flow<Resource<AuthResponse>>{
        return apiRepository.refreshToken()
    }

    suspend fun updateProfile(jsonObject: JsonObject): Flow<Resource<AuthResponse>> {
        return apiRepository.updateProfile(jsonObject)
    }

    suspend fun updateProfileImage(jsonObject: JsonObject): Flow<Resource<AuthResponse>> {
        return apiRepository.updateProfileImage(jsonObject)
    }

    suspend fun getProfile(): Flow<Resource<AuthResponse>> {
        return apiRepository.getProfile()
    }

    suspend fun userLogout(): Flow<Resource<AuthResponse>> {
        return apiRepository.userLogout()
    }

    suspend fun getCourseList(): Flow<Resource<CourseResponse>> {
        return apiRepository.getCourseList()
    }

    suspend fun searchCourse(searchValue: String): Flow<Resource<CourseResponse>> {
        return apiRepository.searchCourse(searchValue)
    }

    suspend fun getServiceList(): Flow<Resource<ServiceResponse>> {
        return apiRepository.getServiceList()
    }

    suspend fun searchService(searchValue: String): Flow<Resource<ServiceResponse>> {
        return apiRepository.searchService(searchValue)
    }

    suspend fun getChapterList(): Flow<Resource<ChapterResponse>> {
        return apiRepository.getChapterList()
    }

    suspend fun searchChapter(searchValue: String): Flow<Resource<ChapterResponse>> {
        return apiRepository.searchChapter(searchValue)
    }

    /**
     * Question APi
     */
    suspend fun getQuestionList(): Flow<Resource<QuestionResponse>> {
        return apiRepository.getQuestionList()
    }

}