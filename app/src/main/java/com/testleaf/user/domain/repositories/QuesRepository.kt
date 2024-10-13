package com.testleaf.user.domain.repositories

import com.testleaf.user.domain.models.Feedback
import com.testleaf.user.domain.models.Question
import com.testleaf.user.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
interface QuesRepository {

    suspend fun getQuestions(
        courseId: String,
        chapterId: String,
        sectionId: String,
        limit: Long
    ): Flow<Resource<List<Question>>>

    suspend fun getQuestions(
        setId: String,
        subSetId: String,
        sectionId: String,
    ): Flow<Resource<List<Question>>>

    suspend fun addToReviewQues(
        question: Question
    ): Flow<Resource<Question>>

    suspend fun getReviewQues(): Flow<Resource<List<Question>>>

    suspend fun deleteReviewQues(quesId: String): Flow<Resource<Question>>

    suspend fun sendQuesFeedback(
        feedback: Feedback
    ): Flow<Resource<Feedback>>

}