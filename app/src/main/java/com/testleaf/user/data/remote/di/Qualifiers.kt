package com.testleaf.user.data.remote.di

import javax.inject.Qualifier

/**
 * Created by Noweshed on 5/11/24.
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenOkHttpQualifier

// retrofit service qualifiers
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenApiQualifier