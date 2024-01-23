package com.awsprep.user.data.remote.repo

import com.awsprep.user.domain.models.Feedback
import com.awsprep.user.domain.models.Question
import com.awsprep.user.domain.repositories.QuesRepository
import com.awsprep.user.utils.AppConstant.COLL_CHAPTERS
import com.awsprep.user.utils.AppConstant.COLL_COURSES
import com.awsprep.user.utils.AppConstant.COLL_FEEDBACK
import com.awsprep.user.utils.AppConstant.COLL_QUESTIONS
import com.awsprep.user.utils.AppConstant.COLL_REVIEW_QUES
import com.awsprep.user.utils.AppConstant.COLL_SECTIONS
import com.awsprep.user.utils.AppConstant.COLL_SETS
import com.awsprep.user.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
class QuesRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val userRef: CollectionReference,
    private val firebaseStorage: FirebaseStorage
) : QuesRepository {

    override suspend fun getQuestions(
        courseId: String,
        chapterId: String,
        sectionId: String,
        limit: Long
    ): Flow<Resource<List<Question>>> = flow {
        emit(Resource.Loading())
        try {

            val questions = firebaseFirestore.collection(COLL_COURSES).document(courseId)
                .collection(COLL_CHAPTERS).document(chapterId)
                .collection(COLL_SECTIONS).document(sectionId)
                .collection(COLL_QUESTIONS).limit(limit).get()
                .await()

            var questionList = emptyList<Question>()

            for (ques in questions) {

                val newQues: Question = ques.toObject(Question::class.java)
                newQues.quesId = ques.id

                questionList = questionList + newQues
            }

            emit(Resource.Success(data = questionList))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Check Your Internet Connection"
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    override suspend fun getQuestions(
        setId: String,
        subSetId: String,
        sectionId: String,
    ): Flow<Resource<List<Question>>> = flow {
        emit(Resource.Loading())
        try {

            val questions = firebaseFirestore.collection(COLL_SETS).document(setId)
                .collection(subSetId).document(sectionId)
                .collection(COLL_QUESTIONS).limit(30).get()
                .await()

            var questionList = emptyList<Question>()

            for (ques in questions) {

                val newQues: Question = ques.toObject(Question::class.java)
                newQues.quesId = ques.id

                questionList = questionList + newQues
            }

            emit(Resource.Success(data = questionList))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Check Your Internet Connection"
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    override suspend fun addToReviewQues(question: Question): Flow<Resource<Question>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {

                userRef.document(firebaseAuth.currentUser!!.uid)
                    .collection(COLL_REVIEW_QUES).document(question.quesId)
                    .set(question)
                    .await()

                emit(Resource.Success(data = question))

            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        }
    }

    override suspend fun getReviewQues(): Flow<Resource<List<Question>>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {

                val questions = userRef.document(firebaseAuth.currentUser!!.uid)
                    .collection(COLL_REVIEW_QUES).get()
                    .await()

                var questionList = emptyList<Question>()

                for (ques in questions) {

                    val newQues: Question = ques.toObject(Question::class.java)
                    newQues.quesId = ques.id

                    questionList = questionList + newQues
                }

                emit(Resource.Success(data = questionList))

            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        }
    }

    override suspend fun deleteReviewQues(quesId: String): Flow<Resource<Question>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {

                userRef.document(firebaseAuth.currentUser!!.uid)
                    .collection(COLL_REVIEW_QUES).document(quesId)
                    .delete()
                    .await()

                emit(Resource.Success(data = Question()))

            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        }
    }

    override suspend fun sendQuesFeedback(feedback: Feedback): Flow<Resource<Feedback>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {

                firebaseFirestore.collection(COLL_FEEDBACK).document()
                    .set(feedback).await()

                emit(Resource.Success(data = feedback))

            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        }
    }

}