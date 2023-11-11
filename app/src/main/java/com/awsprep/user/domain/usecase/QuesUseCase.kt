package com.awsprep.user.domain.usecase

import com.awsprep.user.domain.models.Question
import com.awsprep.user.domain.repositories.QuesRepository
import com.awsprep.user.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
class QuesUseCase @Inject constructor(
    private val quesRepository: QuesRepository
) {

    suspend fun getQuestions(
        courseId: String,
        chapterId: String,
        sectionId: String
    ): Flow<Resource<List<Question>>> {
        return quesRepository.getQuestions(courseId, chapterId, sectionId)
    }
}