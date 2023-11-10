package com.awsprep.user.data.di

import com.awsprep.user.data.repo.UserRepositoryImpl
import com.awsprep.user.domain.repositories.AuthRepository
import com.awsprep.user.domain.repositories.UserRepository
import com.awsprep.user.utils.AppConstant.COLL_USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.awsprep.user.data.repo.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by noweshedakram on 14/7/23.
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provideUserRef() = Firebase.firestore.collection(COLL_USER)

    @Singleton
    @Provides
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        userRef: CollectionReference
    ): AuthRepository = AuthRepositoryImpl(
        firebaseAuth,
        userRef
    )

    @Singleton
    @Provides
    fun provideUserRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
        userRef: CollectionReference,
    ): UserRepository = UserRepositoryImpl(
        firebaseAuth,
        firebaseFirestore,
        firebaseStorage,
        userRef
    )

}