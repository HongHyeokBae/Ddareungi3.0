package com.example.ddareungi.di

import com.example.ddareungi.api.BikeStationService
import com.example.ddareungi.api.ParkService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideBikeStationService(okHttpClient: OkHttpClient): BikeStationService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://www.bikeseoul.com/")
            .client(okHttpClient)
            .build()
            .create(BikeStationService::class.java)
    }

    @Singleton
    @Provides
    fun provideParkService(okHttpClient: OkHttpClient): ParkService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://openapi.seoul.go.kr:8088/527a4a4b47627a74363558734a7658/json/")
            .client(okHttpClient)
            .build()
            .create(ParkService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
}