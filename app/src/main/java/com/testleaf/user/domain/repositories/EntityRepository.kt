package com.testleaf.user.domain.repositories

import androidx.lifecycle.LiveData
import com.testleaf.user.data.local.entity.NotificationEntity
import com.testleaf.user.data.local.entity.TestEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */
interface EntityRepository {

    suspend fun insertTestData(testEntity: TestEntity)

    fun getTestMark(marks: Int): LiveData<Int>

    suspend fun clearLocalDb(): Void

    suspend fun insertNotification(notificationEntity: NotificationEntity)

    fun getNotifications(): Flow<List<NotificationEntity>>

    fun updateNotificationReadStatus(id: Int, isSeen: Boolean): Int

}