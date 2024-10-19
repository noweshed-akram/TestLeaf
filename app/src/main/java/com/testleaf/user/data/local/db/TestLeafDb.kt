package com.testleaf.user.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testleaf.user.data.local.dao.EntityDao
import com.testleaf.user.data.local.entity.NotificationEntity
import com.testleaf.user.data.local.entity.TestEntity
import com.testleaf.user.data.local.entity.UserEntity

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */
@Database(
    entities = [UserEntity::class, TestEntity::class, NotificationEntity::class],
    exportSchema = false,
    version = 1
)
abstract class TestLeafDb : RoomDatabase() {

    abstract fun entityDao(): EntityDao

    companion object {

        @Volatile
        private var INSTANCE: TestLeafDb? = null

        fun getDatabase(context: Context): TestLeafDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestLeafDb::class.java,
                    "com.testleaf.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}