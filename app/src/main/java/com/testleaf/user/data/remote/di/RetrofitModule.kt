package com.testleaf.user.data.remote.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.testleaf.user.data.remote.api.ApiService
import com.testleaf.user.data.remote.api.TokenService
import com.testleaf.user.utils.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Noweshed on 28/9/24.
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext

    /**
     * Provides BaseUrl as string
     */
    @Singleton
    @Provides
    fun provideBaseURL(): String {
        return AppConstant.BASE_URL
    }

    /**
     * Provides LoggingInterceptor for api information
     */
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideCurlLoggerInterceptor() = CurlLoggingInterceptor()

    @Singleton
    @Provides
    fun provideHostUrlInterceptor(): HostUrlInterceptor {
        return HostUrlInterceptor()
    }

    /**
     * Provides custom OkkHttp
     */
    @Singleton
    @Provides
    @OkHttpQualifier
    fun provideOkHttpClient(
        @ApplicationContext appContext: Context,
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        hostUrlInterceptor: HostUrlInterceptor,
        curlLoggingInterceptor: CurlLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(hostUrlInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(curlLoggingInterceptor)
            .addInterceptor(ChuckerInterceptor(appContext))

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    @TokenOkHttpQualifier
    fun provideTokenOkHttpClient(
        @ApplicationContext appContext: Context,
        loggingInterceptor: HttpLoggingInterceptor,
        hostUrlInterceptor: HostUrlInterceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(hostUrlInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ChuckerInterceptor(appContext))
            .addInterceptor(Interceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", AppConstant.AUTH_TOKEN)
                    .build()
                chain.proceed(newRequest)
            })

        return okHttpClient.build()
    }

    /**
     * Provides converter factory for retrofit
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    @ApiQualifier
    fun provideRetrofitClient(
        baseUrl: String,
        @OkHttpQualifier okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    @TokenApiQualifier
    fun provideTokenRetrofitClient(
        baseUrl: String,
        @TokenOkHttpQualifier okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
    }

    /**
     * Provides Api Service using retrofit
     */
    @Singleton
    @Provides
    fun provideRestApiService(@ApiQualifier retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTokenApiService(@TokenApiQualifier retrofit: Retrofit): TokenService {
        return retrofit.create(TokenService::class.java)
    }
}