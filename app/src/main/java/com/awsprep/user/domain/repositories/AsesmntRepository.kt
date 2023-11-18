package com.awsprep.user.domain.repositories

import com.awsprep.user.domain.models.Course
import com.awsprep.user.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
interface AsesmntRepository {

    suspend fun getCourseList(limit: Long): Flow<Resource<List<Course>>>

    suspend fun getChapterList(courseId: String): Flow<Resource<List<Course>>>

    suspend fun getSectionList(courseId: String, chapterId: String): Flow<Resource<List<Course>>>

}