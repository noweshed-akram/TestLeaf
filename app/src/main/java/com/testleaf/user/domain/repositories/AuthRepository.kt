package com.testleaf.user.domain.repositories

import com.testleaf.user.utils.Resource
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.FirebaseUser
import com.testleaf.user.domain.models.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by noweshedakram on 14/7/23.
 */

typealias OneTapSignInResponse = Resource<BeginSignInResult>
typealias SignInWithGoogleResponse = Resource<Boolean>

interface AuthRepository {

    /**
     * Firebase Sign in Methods for Email and Password
     */
    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        user: User
    ): Flow<Resource<FirebaseUser>>

    suspend fun sendEmailVerification(): Resource<Boolean>

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser>>

    suspend fun sendPasswordResetEmail(email: String): Resource<Boolean>
    /**
     * End of
     * Firebase Sign in Methods for Email and Password
     */
}