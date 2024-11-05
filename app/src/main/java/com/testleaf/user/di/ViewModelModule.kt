package com.testleaf.user.di

import android.app.Application
import com.testleaf.user.domain.usecase.ApiUseCase
import com.testleaf.user.domain.usecase.EntityUseCase
import com.testleaf.user.domain.usecase.UserUseCase
import com.testleaf.user.viewmodel.AsesmntViewModel
import com.testleaf.user.viewmodel.AuthViewModel
import com.testleaf.user.viewmodel.EntityViewModel
import com.testleaf.user.viewmodel.QuesViewModel
import com.testleaf.user.viewmodel.UserViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by noweshedakram on 24/6/23.
 */
@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Singleton
    @Provides
    fun providesAuthViewModel(
        app: Application,
        apiUseCase: ApiUseCase
    ): AuthViewModel {
        return AuthViewModel(app, apiUseCase)
    }

    @Singleton
    @Provides
    fun providesUserViewModel(
        app: Application,
        userUseCase: UserUseCase,
    ): UserViewModel {
        return UserViewModel(app, userUseCase)
    }

    @Singleton
    @Provides
    fun providesAsesmntViewModel(
        app: Application,
        apiUseCase: ApiUseCase
    ): AsesmntViewModel {
        return AsesmntViewModel(app, apiUseCase)
    }

    @Singleton
    @Provides
    fun providesQuesViewModel(
        app: Application,
        apiUseCase: ApiUseCase
    ): QuesViewModel {
        return QuesViewModel(app, apiUseCase)
    }

    @Singleton
    @Provides
    fun providesEntityViewModel(
        app: Application,
        entityUseCase: EntityUseCase
    ): EntityViewModel {
        return EntityViewModel(app, entityUseCase)
    }

}