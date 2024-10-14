package com.testleaf.user.data.local.di

import android.content.Context
import com.testleaf.user.data.local.dao.EntityDao
import com.testleaf.user.data.local.db.TestLeafDb
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
    fun provideAppDatabase(@ApplicationContext context: Context): TestLeafDb {
        return TestLeafDb.getDatabase(context)
    }

    @Singleton
    @Provides
    fun providesEntityDao(testLeafDb: TestLeafDb): EntityDao {
        return testLeafDb.entityDao()
    }

}