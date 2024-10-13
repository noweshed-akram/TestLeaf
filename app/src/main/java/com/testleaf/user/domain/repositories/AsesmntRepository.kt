package com.testleaf.user.domain.repositories

import com.testleaf.user.domain.models.Acronyms
import com.testleaf.user.domain.models.Course
import com.testleaf.user.domain.models.Definition
import com.testleaf.user.domain.models.Set
import com.testleaf.user.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
interface AsesmntRepository {

    suspend fun getCourseList(limit: Long): Flow<Resource<List<Course>>>

    suspend fun getChapterList(courseId: String): Flow<Resource<List<Course>>>

    suspend fun getSectionList(courseId: String, chapterId: String): Flow<Resource<List<Course>>>

    suspend fun getSetList(): Flow<Resource<List<Set>>>

    suspend fun getSubSetList(setId: String, subSetId: String): Flow<Resource<List<Course>>>

    suspend fun getAcronyms():Flow<Resource<List<Acronyms>>>

    suspend fun getDefinition():Flow<Resource<List<Definition>>>

}