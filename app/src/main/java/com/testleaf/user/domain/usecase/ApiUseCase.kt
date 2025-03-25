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

    /**
     * User API
     */
    suspend fun userRegistration(jsonObject: JsonObject): Flow<Resource<AuthResponse>> {
        return apiRepository.userRegistration(jsonObject)
    }

    suspend fun userLogin(jsonObject: JsonObject): Flow<Resource<AuthResponse>> {
        return apiRepository.userLogin(jsonObject)
    }

    suspend fun refreshToken(): Flow<Resource<AuthResponse>> {
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

    /**
     * Segments API
     */
    suspend fun getCourseList(): Flow<Resource<CourseResponse>> {
        return apiRepository.getCourseList()
    }

    suspend fun getCourseList(limit: Int): Flow<Resource<CourseResponse>> {
        return apiRepository.getCourseList(limit)
    }

    suspend fun getServiceList(): Flow<Resource<ServiceResponse>> {
        return apiRepository.getServiceList()
    }

    suspend fun getServiceList(limit: Int): Flow<Resource<ServiceResponse>> {
        return apiRepository.getServiceList(limit)
    }

    suspend fun getChapterList(): Flow<Resource<ChapterResponse>> {
        return apiRepository.getChapterList()
    }

    suspend fun getChapterList(limit: Int): Flow<Resource<ChapterResponse>> {
        return apiRepository.getChapterList(limit)
    }

    /**
     * Question API
     */
    suspend fun getQuestionList(limit: Int): Flow<Resource<QuestionResponse>> {
        return apiRepository.getQuestionList(limit)
    }

    suspend fun getQuestionByCourse(
        courseId: Int,
        limit: Int
    ): Flow<Resource<QuestionResponse>> {
        return apiRepository.getQuestionByCourse(courseId, limit)
    }

    suspend fun getQuestionByChapter(
        chapterId: Int,
        limit: Int
    ): Flow<Resource<QuestionResponse>> {
        return apiRepository.getQuestionByChapter(chapterId, limit)
    }

    suspend fun getQuestionBySection(
        sectionId: Int,
        limit: Int
    ): Flow<Resource<QuestionResponse>> {
        return apiRepository.getQuestionBySection(sectionId, limit)
    }

}