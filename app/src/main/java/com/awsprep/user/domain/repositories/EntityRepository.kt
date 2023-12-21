package com.awsprep.user.domain.repositories

import androidx.lifecycle.LiveData
import com.awsprep.user.data.local.entity.TestEntity

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */
interface EntityRepository {

    suspend fun insertTestData(testEntity: TestEntity)

    fun getTestMark(marks: Int): LiveData<Int>

    suspend fun getSelectedAns(quesId: String): String

    suspend fun clearLocalDb(): Int
}