package com.example.hearthstone.di

import com.example.hearthstone.model.data.CardsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun fetchHearthStoneAPI(): CardsApi {
        val loginInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(loginInterceptor).readTimeout(5, TimeUnit.MINUTES)
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CardsApi.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClientBuilder.build())
            .build()
            .create(CardsApi::class.java)
    }

}