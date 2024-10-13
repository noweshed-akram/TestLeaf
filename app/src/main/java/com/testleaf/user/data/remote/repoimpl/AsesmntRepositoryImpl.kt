package com.testleaf.user.data.remote.repoimpl

import com.testleaf.user.domain.models.Acronyms
import com.testleaf.user.domain.models.Course
import com.testleaf.user.domain.models.Definition
import com.testleaf.user.domain.models.Set
import com.testleaf.user.domain.repositories.AsesmntRepository
import com.testleaf.user.utils.AppConstant.COLL_ACRONYMS
import com.testleaf.user.utils.AppConstant.COLL_CHAPTERS
import com.testleaf.user.utils.AppConstant.COLL_COURSES
import com.testleaf.user.utils.AppConstant.COLL_DEFINITIONS
import com.testleaf.user.utils.AppConstant.COLL_SECTIONS
import com.testleaf.user.utils.AppConstant.COLL_SETS
import com.testleaf.user.utils.AppConstant.FIELD_ACTIVE
import com.testleaf.user.utils.AppConstant.FIELD_ORDER
import com.testleaf.user.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Singleton
class AsesmntRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : AsesmntRepository {

    override suspend fun getCourseList(limit: Long): Flow<Resource<List<Course>>> = flow {
        emit(Resource.Loading())
        try {

            val courses =
                firebaseFirestore.collection(COLL_COURSES).whereEqualTo(FIELD_ACTIVE, true)
                    .limit(limit).orderBy(FIELD_ORDER).get()
                    .await()
            var courseList = emptyList<Course>()

            for (course in courses) {

                val newCourse: Course = course.toObject(Course::class.java)
                newCourse.docId = course.id

                courseList = courseList + newCourse
            }

            emit(Resource.Success(data = courseList))

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

    override suspend fun getChapterList(courseId: String): Flow<Resource<List<Course>>> = flow {
        emit(Resource.Loading())
        try {

            val chapters = firebaseFirestore.collection(COLL_COURSES).document(courseId)
                .collection(COLL_CHAPTERS).whereEqualTo(FIELD_ACTIVE, true).orderBy(FIELD_ORDER)
                .get().await()
            var chapterList = emptyList<Course>()

            for (chapter in chapters) {

                val newChapter: Course = chapter.toObject(Course::class.java)
                newChapter.docId = chapter.id

                chapterList = chapterList + newChapter
            }

            emit(Resource.Success(data = chapterList))

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

    override suspend fun getSectionList(
        courseId: String,
        chapterId: String
    ): Flow<Resource<List<Course>>> = flow {
        emit(Resource.Loading())
        try {
            val sections = firebaseFirestore.collection(COLL_COURSES).document(courseId)
                .collection(COLL_CHAPTERS).document(chapterId).collection(COLL_SECTIONS)
                .whereEqualTo(FIELD_ACTIVE, true)
                .orderBy(FIELD_ORDER).get().await()
            var sectionList = emptyList<Course>()

            for (section in sections) {

                val newSection: Course = section.toObject(Course::class.java)
                newSection.docId = section.id

                sectionList = sectionList + newSection
            }

            emit(Resource.Success(data = sectionList))
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

    override suspend fun getSetList(): Flow<Resource<List<Set>>> = flow {
        emit(Resource.Loading())
        try {

            val sets = firebaseFirestore.collection(COLL_SETS)
                .whereEqualTo(FIELD_ACTIVE, true)
                .orderBy(FIELD_ORDER).get()
                .await()
            var setList = emptyList<Set>()

            for (set in sets) {

                val newSet: Set = set.toObject(Set::class.java)
                newSet.setId = set.id

                setList = setList + newSet
            }

            emit(Resource.Success(data = setList))

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

    override suspend fun getSubSetList(
        setId: String,
        subSetId: String
    ): Flow<Resource<List<Course>>> = flow {
        emit(Resource.Loading())
        try {

            val sets = firebaseFirestore.collection(COLL_SETS).document(setId).collection(subSetId)
                .whereEqualTo(FIELD_ACTIVE, true)
                .orderBy(FIELD_ORDER).get()
                .await()
            var setList = emptyList<Course>()

            for (set in sets) {

                val newSet: Course = set.toObject(Course::class.java)
                newSet.docId = set.id

                setList = setList + newSet
            }

            emit(Resource.Success(data = setList))

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

    override suspend fun getAcronyms(): Flow<Resource<List<Acronyms>>> = flow {
        emit(Resource.Loading())
        try {

            val acronyms = firebaseFirestore.collection(COLL_ACRONYMS).get().await()

            var acronymList = emptyList<Acronyms>()

            for (acronym in acronyms) {

                val newAcronym: Acronyms = acronym.toObject(Acronyms::class.java)
                newAcronym.docId = acronym.id

                acronymList = acronymList + newAcronym
            }

            emit(Resource.Success(data = acronymList))

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

    override suspend fun getDefinition(): Flow<Resource<List<Definition>>> = flow {
        emit(Resource.Loading())
        try {

            val definitions = firebaseFirestore.collection(COLL_DEFINITIONS).get().await()

            var definitionList = emptyList<Definition>()

            for (definition in definitions) {

                val newDefinition: Definition = definition.toObject(Definition::class.java)
                newDefinition.docId = definition.id

                definitionList = definitionList + newDefinition
            }

            emit(Resource.Success(data = definitionList))

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