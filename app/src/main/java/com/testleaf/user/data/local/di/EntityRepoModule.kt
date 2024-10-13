package com.testleaf.user.data.local.di

import com.testleaf.user.data.local.repoimpl.EntityRepoImpl
import com.testleaf.user.domain.repositories.EntityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class EntityRepoModule {

    @Binds
    @Singleton
    abstract fun bindsEntityRepo(
        entityRepoImpl: EntityRepoImpl
    ): EntityRepository

}