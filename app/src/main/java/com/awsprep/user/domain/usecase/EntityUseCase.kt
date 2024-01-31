package com.awsprep.user.domain.usecase

import androidx.lifecycle.LiveData
import com.awsprep.user.data.local.entity.NotificationEntity
import com.awsprep.user.data.local.entity.TestEntity
import com.awsprep.user.domain.repositories.EntityRepository
import kotlinx.coroutines.flow.Flow
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

    fun getTestMark(marks: Int): LiveData<Int> {
        return entityRepository.getTestMark(marks)
    }

    suspend fun clearLocalDb(): Int {
        return entityRepository.clearLocalDb()
    }

    suspend fun insertNotification(notificationEntity: NotificationEntity) {
        return entityRepository.insertNotification(notificationEntity)
    }

    fun getNotifications(): Flow<List<NotificationEntity>> {
        return entityRepository.getNotifications()
    }

    fun updateNotificationReadStatus(id: Int, isSeen: Boolean): Int {
        return entityRepository.updateNotificationReadStatus(id, isSeen)
    }

}