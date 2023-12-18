package com.awsprep.user.domain.usecase

import com.awsprep.user.data.local.entity.TestEntity
import com.awsprep.user.domain.repositories.EntityRepository
import javax.inject.Inject

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */
class EntityUseCase @Inject constructor(
    private val entityRepository: EntityRepository
) {

    suspend fun insertTestData(testEntity: TestEntity) {
        entityRepository.insertTestData(testEntity)
    }

    suspend fun getTestMark(marks: Int): Int {
        return entityRepository.getTestMark(marks)
    }

    suspend fun getSelectedAns(quesId: String): String {
        return entityRepository.getSelectedAns(quesId)
    }

    suspend fun clearLocalDb(): Int {
        return entityRepository.clearLocalDb()
    }

}