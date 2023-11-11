package com.awsprep.user.domain.repositories

import com.awsprep.user.domain.models.Question
import com.awsprep.user.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
interface QuesRepository {

    suspend fun getQuestions(
        courseId: String,
        chapterId: String,
        sectionId: String
    ): Flow<Resource<List<Question>>>

}