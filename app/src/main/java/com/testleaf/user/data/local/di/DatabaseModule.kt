package com.testleaf.user.data.local.di

import android.content.Context
import com.testleaf.user.data.local.dao.EntityDao
import com.testleaf.user.data.local.db.TestPrepDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): TestPrepDb {
        return TestPrepDb.getDatabase(context)
    }

    @Singleton
    @Provides
    fun providesEntityDao(testPrepDb: TestPrepDb): EntityDao {
        return testPrepDb.entityDao()
    }

}