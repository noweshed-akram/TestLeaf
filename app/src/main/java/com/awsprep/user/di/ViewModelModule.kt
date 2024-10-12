package com.awsprep.user.di

import android.app.Application
import com.awsprep.user.domain.usecase.ApiUseCase
import com.awsprep.user.domain.usecase.AsesmntUseCase
import com.awsprep.user.domain.usecase.AuthUseCase
import com.awsprep.user.domain.usecase.EntityUseCase
import com.awsprep.user.domain.usecase.QuesUseCase
import com.awsprep.user.domain.usecase.UserUseCase
import com.awsprep.user.viewmodel.ApiViewModel
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.AuthViewModel
import com.awsprep.user.viewmodel.EntityViewModel
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.UserViewModel
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
        authUseCase: AuthUseCase
    ): AuthViewModel {
        return AuthViewModel(app, authUseCase)
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
        asesmntUseCase: AsesmntUseCase
    ): AsesmntViewModel {
        return AsesmntViewModel(app, asesmntUseCase)
    }

    @Singleton
    @Provides
    fun providesQuesViewModel(
        app: Application,
        quesUseCase: QuesUseCase
    ): QuesViewModel {
        return QuesViewModel(app, quesUseCase)
    }

    @Singleton
    @Provides
    fun providesEntityViewModel(
        app: Application,
        entityUseCase: EntityUseCase
    ): EntityViewModel {
        return EntityViewModel(app, entityUseCase)
    }

    @Singleton
    @Provides
    fun providesApiViewModel(
        app: Application,
        apiUseCase: ApiUseCase
    ): ApiViewModel {
        return ApiViewModel(app, apiUseCase)
    }

}