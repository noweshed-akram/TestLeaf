package com.awsprep.user.domain.usecase

import com.awsprep.user.domain.models.Chapter
import com.awsprep.user.domain.models.Course
import com.awsprep.user.domain.models.Section
import com.awsprep.user.domain.repositories.AsesmntRepository
import com.awsprep.user.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
class AsesmntUseCase @Inject constructor(
    private val asesmntRepository: AsesmntRepository
) {


    suspend fun getCourseList(): Flow<Resource<List<Course>>> {
        return asesmntRepository.getCourseList()
    }

    suspend fun getChapterList(courseId: String): Flow<Resource<List<Chapter>>> {
        return asesmntRepository.getChapterList(courseId)
    }

    suspend fun getSectionList(courseId: String, chapterId: String): Flow<Resource<List<Section>>> {
        return asesmntRepository.getSectionList(courseId, chapterId)
    }

}