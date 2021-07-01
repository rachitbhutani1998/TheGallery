package com.example.myapplication.data.repository

import com.example.myapplication.data.model.PhotoResponse
import com.example.myapplication.data.service.FlickrService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FlickrRepository
@Inject
constructor(private val mService: FlickrService) {

    suspend fun loadImages(query: String = "", page: Int = 1): PhotoResponse? {
        return withContext(Dispatchers.IO) {
            mService.getImages(query = query, page = page).photos
        }
    }

}