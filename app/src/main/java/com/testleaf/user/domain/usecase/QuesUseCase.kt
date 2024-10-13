package com.testleaf.user.domain.usecase

import com.testleaf.user.domain.models.Feedback
import com.testleaf.user.domain.models.Question
import com.testleaf.user.domain.repositories.QuesRepository
import com.testleaf.user.utils.Resource
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

    suspend fun getQuestions(
        setId: String,
        subSetId: String,
        sectionId: String,
    ): Flow<Resource<List<Question>>> {
        return quesRepository.getQuestions(setId, subSetId, sectionId)
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