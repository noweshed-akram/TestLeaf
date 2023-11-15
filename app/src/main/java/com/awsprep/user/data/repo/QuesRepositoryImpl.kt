package com.awsprep.user.data.repo

import com.awsprep.user.domain.models.Question
import com.awsprep.user.domain.repositories.QuesRepository
import com.awsprep.user.utils.AppConstant.COLL_CHAPTERS
import com.awsprep.user.utils.AppConstant.COLL_COURSES
import com.awsprep.user.utils.AppConstant.COLL_QUESTIONS
import com.awsprep.user.utils.AppConstant.COLL_SECTIONS
import com.awsprep.user.utils.Resource
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
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : QuesRepository {

    override suspend fun getQuestions(
        courseId: String,
        chapterId: String,
        sectionId: String
    ): Flow<Resource<List<Question>>> = flow {
        emit(Resource.Loading())
        try {

            val questions = firebaseFirestore.collection(COLL_COURSES).document(courseId)
                .collection(COLL_CHAPTERS).document(chapterId).collection(COLL_SECTIONS)
                .document(sectionId).collection(COLL_QUESTIONS).limit(1).get()
                .await()

            var questionList = emptyList<Question>()

            for (ques in questions) {

                val newQues: Question = ques.toObject(Question::class.java)

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