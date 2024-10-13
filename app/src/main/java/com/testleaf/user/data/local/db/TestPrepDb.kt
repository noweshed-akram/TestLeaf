package com.testleaf.user.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testleaf.user.data.local.dao.EntityDao
import com.testleaf.user.data.local.entity.NotificationEntity
import com.testleaf.user.data.local.entity.TestEntity

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */
@Database(
    entities = [TestEntity::class, NotificationEntity::class],
    exportSchema = false,
    version = 1
)
abstract class TestPrepDb : RoomDatabase() {

    abstract fun entityDao(): EntityDao

    companion object {

        @Volatile
        private var INSTANCE: TestPrepDb? = null

        fun getDatabase(context: Context): TestPrepDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestPrepDb::class.java,
                    "com.aws.test_prep_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}