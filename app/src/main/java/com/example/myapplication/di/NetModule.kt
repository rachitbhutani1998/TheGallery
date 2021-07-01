package com.example.myapplication.di

import com.example.myapplication.data.service.FlickrService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    companion object {
        const val BASE_URL = "https://api.flickr.com"
    }

    @Provides
    fun provideFlickrService(): FlickrService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(FlickrService::class.java)
    }
}