package com.testleaf.user.domain.usecase

import com.testleaf.user.utils.Resource
import com.google.firebase.auth.FirebaseUser
import com.testleaf.user.domain.models.User
import com.testleaf.user.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by noweshedakram on 14/7/23.
 */
class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    /**
     * Firebase Sign in Methods for Email and Password
     */
    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        user: User
    ): Flow<Resource<FirebaseUser>> {
        return authRepository.signUpWithEmailAndPassword(email, password, user)
    }

    suspend fun sendEmailVerification(): Resource<Boolean> {
        return authRepository.sendEmailVerification()
    }

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser>> {
        return authRepository.signInWithEmailAndPassword(email, password)
    }

    suspend fun sendPasswordResetEmail(email: String): Resource<Boolean> {
        return authRepository.sendPasswordResetEmail(email)
    }
    /**
     * End of
     * Firebase Sign in Methods for Email and Password
     */

}