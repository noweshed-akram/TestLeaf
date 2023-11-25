package com.awsprep.user.domain.usecase

import com.awsprep.user.domain.models.Feedback
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
        sectionId: String,
        limit: Long
    ): Flow<Resource<List<Question>>> {
        return quesRepository.getQuestions(courseId, chapterId, sectionId, limit)
    }

    suspend fun addToReviewQues(
        question: Question
    ): Flow<Resource<Question>> {
        return quesRepository.addToReviewQues(question)
    }

    suspend fun getReviewQues(): Flow<Resource<List<Question>>> {
        return quesRepository.getReviewQues()
    }

    suspend fun deleteReviewQues(quesId: String): Flow<Resource<Question>> {
        return quesRepository.deleteReviewQues(quesId)
    }

    suspend fun sendQuesFeedback(
        feedback: Feedback
    ): Flow<Resource<Feedback>> {
        return quesRepository.sendQuesFeedback(feedback)
    }
}