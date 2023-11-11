package com.awsprep.user.data.repo

import android.net.Uri
import com.awsprep.user.domain.models.User
import com.awsprep.user.domain.repositories.UserRepository
import com.awsprep.user.utils.AppConstant.DATE_TIME_FORMAT
import com.awsprep.user.utils.AppConstant.FIELD_ADDRESS
import com.awsprep.user.utils.AppConstant.FIELD_EMAIL
import com.awsprep.user.utils.AppConstant.FIELD_IMAGE_URL
import com.awsprep.user.utils.AppConstant.FIELD_NAME
import com.awsprep.user.utils.AppConstant.FIELD_PHONE
import com.awsprep.user.utils.AppConstant.FIELD_UPDATED_AT
import com.awsprep.user.utils.AppConstant.STORAGE_PROFILE_PIC
import com.awsprep.user.utils.Resource
import com.awsprep.user.utils.getCurrentDateTime
import com.awsprep.user.utils.toString
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
import javax.inject.Singleton


/**
 * Created by noweshedakram on 29/7/23.
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    private val userRef: CollectionReference,
) : UserRepository {

    override val isUserAuthenticatedInFirebase = firebaseAuth.currentUser != null

    override fun getUserData(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {
                val snapshot = userRef.document(firebaseAuth.currentUser!!.uid).get().await()
                if (snapshot.exists()) {
                    val user: User? = snapshot.toObject(User::class.java)
                    emit(Resource.Success(data = user!!))
                }
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

    override fun updateUser(user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {

                val update: MutableMap<String, Any> = HashMap()

                if (user.name.isNotEmpty()) {
                    update[FIELD_NAME] = user.name
                }
                if (user.email.isNotEmpty()) {
                    update[FIELD_EMAIL] = user.email
                }
                if (user.phone.isNotEmpty()) {
                    update[FIELD_PHONE] = user.phone
                }
                if (user.address.isNotEmpty()) {
                    update[FIELD_ADDRESS] = user.address
                }

                update[FIELD_UPDATED_AT] = getCurrentDateTime().toString(DATE_TIME_FORMAT)

                userRef.document(firebaseAuth.currentUser!!.uid).update(update).await()

                val snapshot = userRef.document(firebaseAuth.currentUser!!.uid).get().await()
                if (snapshot.exists()) {
                    val user: User? = snapshot.toObject(User::class.java)
                    emit(Resource.Success(data = user!!))
                }

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

    override fun updateProfilePic(imageUri: Uri): Flow<Resource<User>> = flow {
        val profilePicStorage = firebaseStorage.getReference().child(STORAGE_PROFILE_PIC)
            .child(firebaseAuth.currentUser!!.uid + ".jpg")
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {

                profilePicStorage.putFile(imageUri).addOnSuccessListener {
                    profilePicStorage.downloadUrl.addOnSuccessListener {
                        userRef.document(firebaseAuth.currentUser!!.uid)
                            .update(FIELD_IMAGE_URL, it.toString())

                        userRef.document(firebaseAuth.currentUser!!.uid)
                            .update(
                                FIELD_UPDATED_AT,
                                getCurrentDateTime().toString(DATE_TIME_FORMAT)
                            )
                    }
                }

                val snapshot = userRef.document(firebaseAuth.currentUser!!.uid).get().await()
                if (snapshot.exists()) {
                    val user: User? = snapshot.toObject(User::class.java)
                    emit(Resource.Success(data = user!!))
                }

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

    override suspend fun logOut() {
        firebaseAuth.signOut()
    }
}