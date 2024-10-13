package com.testleaf.user.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testleaf.user.data.local.entity.NotificationEntity
import com.testleaf.user.data.local.entity.TestEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */

@Dao
interface EntityDao {

    /**
     * Test Entity
     * Store users answer during the exam to calculate scores
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestData(testEntity: TestEntity)

    @Query("select count(*) from testentity where marks =:marks ")
    fun getTestMark(marks: Int): LiveData<Int>

    @Query("delete from testentity")
    suspend fun clearLocalDb(): Void

    /**
     * Notification Entity
     * Store notification locally to view later
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notificationEntity: NotificationEntity)

    @Query("select * from notification")
    fun getNotifications(): Flow<List<NotificationEntity>>

    @Query("update notification set isSeen=:isSeen where notificationId=:id")
    fun updateNotificationReadStatus(id: Int, isSeen: Boolean): Int

}