package com.testleaf.user.domain.usecase

import com.testleaf.user.domain.models.Acronyms
import com.testleaf.user.domain.models.Course
import com.testleaf.user.domain.models.Definition
import com.testleaf.user.domain.models.Set
import com.testleaf.user.domain.repositories.AsesmntRepository
import com.testleaf.user.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
class AsesmntUseCase @Inject constructor(
    private val asesmntRepository: AsesmntRepository
) {

    suspend fun getCourseList(limit: Long): Flow<Resource<List<Course>>> {
        return asesmntRepository.getCourseList(limit = limit)
    }

    suspend fun getChapterList(courseId: String): Flow<Resource<List<Course>>> {
        return asesmntRepository.getChapterList(courseId)
    }

    suspend fun getSectionList(courseId: String, chapterId: String): Flow<Resource<List<Course>>> {
        return asesmntRepository.getSectionList(courseId, chapterId)
    }

    suspend fun getSetList(): Flow<Resource<List<Set>>> {
        return asesmntRepository.getSetList()
    }

    suspend fun getSubSetList(setId: String, subSetId: String): Flow<Resource<List<Course>>> {
        return asesmntRepository.getSubSetList(setId, subSetId)
    }

    suspend fun getAcronyms(): Flow<Resource<List<Acronyms>>> {
        return asesmntRepository.getAcronyms()
    }

    suspend fun getDefinition(): Flow<Resource<List<Definition>>> {
        return asesmntRepository.getDefinition()
    }

}