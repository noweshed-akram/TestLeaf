package com.awsprep.user.domain.usecase

import android.net.Uri
import com.awsprep.user.domain.models.User
import com.awsprep.user.domain.repositories.UserRepository
import com.awsprep.user.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by noweshedakram on 29/7/23.
 */
class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    val isUserAuthenticatedInFirebase = userRepository.isUserAuthenticatedInFirebase

    fun getUserData(): Flow<Resource<User>> {
        return userRepository.getUserData()
    }

    fun updateUser(user: User): Flow<Resource<User>> {
        return userRepository.updateUser(user)
    }

    fun updateProfilePic(imageUri: Uri): Flow<Resource<User>> {
        return userRepository.updateProfilePic(imageUri)
    }

    suspend fun logOut() {
        userRepository.logOut()
    }
}