package com.awsprep.user.di

import com.awsprep.user.data.remote.api.ApiService
import com.awsprep.user.data.remote.repoimpl.ApiRepoImpl
import com.awsprep.user.domain.repositories.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Noweshed on 12/10/24.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideApiRepository(
        apiService: ApiService
    ): ApiRepository {
        return ApiRepoImpl(
            apiService
        )
    }

}