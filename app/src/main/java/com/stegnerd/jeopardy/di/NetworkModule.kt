package com.stegnerd.jeopardy.di

import com.stegnerd.jeopardy.BuildConfig
import com.stegnerd.jeopardy.data.api.ApiClient
import com.stegnerd.jeopardy.data.api.ApiClientImpl
import com.stegnerd.jeopardy.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


/**
 * Di module of dagger-hilt for networking classes
 *
 * we use provides annotation because it contains 3rd party libraries that are not ours we must inject them
 * we use the singleton annotation because we only want to call this once per application start
 * we use the install in Application component to say that anything in the app can use this module
 *
 * We inject an instance of ApiClient in to services because we inject an instance of provideApiClient
 * which inherits ApiClient
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    private const val BASE_API_URL = "http://jservice.io/api/"

    @Provides
    fun provideBaseApiUrl() = BASE_API_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = if(BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else OkHttpClient
            .Builder()
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_API_URL: String ): Retrofit =
        Retrofit
            .Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_API_URL)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiClient(apiClient: ApiClientImpl): ApiClient = apiClient
}