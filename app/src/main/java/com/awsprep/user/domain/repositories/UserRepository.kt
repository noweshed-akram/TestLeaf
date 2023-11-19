package com.awsprep.user.domain.repositories

import android.net.Uri
import com.awsprep.user.domain.models.TestResult
import com.awsprep.user.domain.models.User
import com.awsprep.user.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by noweshedakram on 29/7/23.
 */
interface UserRepository {

    val isUserAuthenticatedInFirebase: Boolean

    /**
     * Get Registered User data from Firebase
     */
    fun getUserData(): Flow<Resource<User>>

    /**
     * Update data
     */
    fun updateUser(user: User): Flow<Resource<User>>

    fun updateProfilePic(imageUri: Uri): Flow<Resource<User>>

    /**
     * Test Result
     */
    suspend fun getTestResult(userUid: String): Flow<Resource<List<TestResult>>>

    suspend fun insertTestResult(userUid: String, testResult: TestResult): Flow<Resource<TestResult>>

    /**
     * Sign Out
     */
    suspend fun logOut()
}