package com.awsprep.user.data.remote.repoimpl

import android.util.Log
import com.awsprep.user.domain.models.User
import com.awsprep.user.domain.repositories.AuthRepository
import com.awsprep.user.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by noweshedakram on 14/7/23.
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRef: CollectionReference
) : AuthRepository {

    private val TAG = "AuthRepositoryImpl"

    /**
     * Firebase Sign in Methods for Email and Password
     */
    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        user: User
    ): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())

        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val isNewUser = result.additionalUserInfo?.isNewUser ?: false

            if (isNewUser) {
                addUserToFirestore(firebaseAuth.currentUser!!.uid, user)
            }

            emit((result.user?.let {
                Resource.Success(data = it)
            }!!))

        } catch (e: HttpException) {
            Log.d("signUpWithEmailAndPassword: ", e.localizedMessage.toString())
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            Log.d("signUpWithEmailAndPassword: ", e.localizedMessage.toString())
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            Log.d("signUpWithEmailAndPassword: ", e.localizedMessage.toString())
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    override suspend fun sendEmailVerification() = try {
        firebaseAuth.currentUser?.sendEmailVerification()?.await()
        Resource.Success(true)
    } catch (e: Exception) {
        Resource.Error(e?.localizedMessage.toString())
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())

        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit((result.user?.let {
                Resource.Success(data = it)
            }!!))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection")
            )
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    override suspend fun sendPasswordResetEmail(email: String) = try {
        firebaseAuth.sendPasswordResetEmail(email).await()
        Resource.Success(true)
    } catch (e: Exception) {
        Resource.Error(e?.localizedMessage.toString())
    }
    /**
     * End of
     * Firebase Sign in Methods for Email and Password
     */

    private suspend fun addUserToFirestore(uid: String, user: User) {
        userRef.document(uid).set(user).await()
    }
}