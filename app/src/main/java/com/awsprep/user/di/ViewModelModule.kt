package com.awsprep.user.di

import android.app.Application
import com.awsprep.user.domain.usecase.AuthUseCase
import com.awsprep.user.domain.usecase.UserUseCase
import com.awsprep.user.viewmodel.AuthViewModel
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

}