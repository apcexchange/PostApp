package com.example.weekten.di

import com.example.weekten.api.ApiServices
import com.example.weekten.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(loggingInterceptor)
        val okhttp = okHttpClient.build()
        return okhttp
    }

    @Singleton
    @Provides
    fun providesGsonBuilder(): Gson {
       return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson,okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
             }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit.Builder): ApiServices {
        return retrofit
            .build()
            .create(ApiServices::class.java)
    }

}