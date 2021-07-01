package com.example.myapplication.data.service

import com.example.myapplication.data.model.FlickrResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {

    @GET("/services/rest")
    suspend fun getImages(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = "062a6c0c49e4de1d78497d13a7dbb360",
        @Query("text") query: String = "",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") callback: Int = 1,
        @Query("per_page") perPageCount: Int = 20,
        @Query("page") page: Int = 1
    ): FlickrResponse
}