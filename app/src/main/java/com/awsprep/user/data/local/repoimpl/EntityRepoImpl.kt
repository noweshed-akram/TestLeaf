package com.awsprep.user.data.local.repoimpl

import androidx.lifecycle.LiveData
import com.awsprep.user.data.local.dao.EntityDao
import com.awsprep.user.data.local.entity.TestEntity
import com.awsprep.user.domain.repositories.EntityRepository
import javax.inject.Inject

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */
class EntityRepoImpl @Inject constructor(
    private val entityDao: EntityDao
) : EntityRepository {

    override suspend fun insertTestData(testEntity: TestEntity) {
        entityDao.insertTestData(testEntity)
    }

    override fun getTestMark(marks: Int): LiveData<Int> {
        return entityDao.getTestMark(marks)
    }

    override suspend fun getSelectedAns(quesId: String): String {
        return entityDao.getSelectedAns(quesId)
    }

    override suspend fun clearLocalDb(): Int {
        return entityDao.clearLocalDb()
    }

}