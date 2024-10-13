package com.testleaf.user.data.local.repoimpl

import androidx.lifecycle.LiveData
import com.testleaf.user.data.local.dao.EntityDao
import com.testleaf.user.data.local.entity.NotificationEntity
import com.testleaf.user.data.local.entity.TestEntity
import com.testleaf.user.domain.repositories.EntityRepository
import kotlinx.coroutines.flow.Flow
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

    override suspend fun clearLocalDb(): Void {
        return entityDao.clearLocalDb()
    }

    override suspend fun insertNotification(notificationEntity: NotificationEntity) {
        return entityDao.insertNotification(notificationEntity)
    }

    override fun getNotifications(): Flow<List<NotificationEntity>> {
        return entityDao.getNotifications()
    }

    override fun updateNotificationReadStatus(id: Int, isSeen: Boolean): Int {
        return entityDao.updateNotificationReadStatus(id, isSeen)
    }

}