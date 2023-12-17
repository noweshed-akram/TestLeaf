package com.awsprep.user.data.remote.di

import com.awsprep.user.data.remote.repo.AsesmntRepositoryImpl
import com.awsprep.user.data.remote.repo.UserRepositoryImpl
import com.awsprep.user.domain.repositories.AuthRepository
import com.awsprep.user.domain.repositories.UserRepository
import com.awsprep.user.utils.AppConstant.COLL_USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.awsprep.user.data.remote.repo.AuthRepositoryImpl
import com.awsprep.user.data.remote.repo.QuesRepositoryImpl
import com.awsprep.user.domain.repositories.AsesmntRepository
import com.awsprep.user.domain.repositories.QuesRepository
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

    @Singleton
    @Provides
    fun providesAsesmntRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
    ): AsesmntRepository = AsesmntRepositoryImpl(
        firebaseFirestore, firebaseStorage
    )

    @Singleton
    @Provides
    fun providesQuesRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        userRef: CollectionReference,
        firebaseStorage: FirebaseStorage
    ): QuesRepository = QuesRepositoryImpl(
        firebaseAuth, firebaseFirestore, userRef, firebaseStorage
    )

}