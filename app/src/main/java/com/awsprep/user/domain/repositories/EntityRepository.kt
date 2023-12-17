package com.awsprep.user.domain.repositories

import com.awsprep.user.data.local.entity.TestEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */
interface EntityRepository {

    suspend fun insertTestData(testEntity: TestEntity)


    suspend fun getTestMark(marks: Int): Int


    suspend fun clearLocalDb(): Int
}