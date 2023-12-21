package com.awsprep.user.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.awsprep.user.data.local.entity.TestEntity

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */

@Dao
interface EntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestData(testEntity: TestEntity)

    @Query("select count(*) from testentity where marks =:marks ")
    fun getTestMark(marks: Int): LiveData<Int>

    @Query("select selectedAnswers from testentity where quesId=:quesId ")
    suspend fun getSelectedAns(quesId: String): String

    @Query("delete from testentity")
    suspend fun clearLocalDb(): Int

}