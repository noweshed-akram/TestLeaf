package com.testleaf.user.domain.usecase

import android.net.Uri
import com.testleaf.user.domain.models.TestResult
import com.testleaf.user.domain.models.User
import com.testleaf.user.domain.repositories.UserRepository
import com.testleaf.user.utils.Resource
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

    suspend fun getTestResult(): Flow<Resource<List<TestResult>>> {
        return userRepository.getTestResult()
    }

    suspend fun insertTestResult(
        testResult: TestResult
    ): Flow<Resource<TestResult>> {
        return userRepository.insertTestResult(testResult)
    }

    suspend fun logOut() {
        userRepository.logOut()
    }
}