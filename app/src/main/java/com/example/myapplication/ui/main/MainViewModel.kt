package com.example.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.ImageModel
import com.example.myapplication.data.repository.FlickrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val flickrRepository: FlickrRepository) : ViewModel() {

    private var savedQuery: String = ""
    private var totalPages: Int = 0
    private var page: MutableLiveData<Int> = MutableLiveData(0)

    companion object {
        const val DEFAULT_PAGE = 1
    }

    private val mImages: MutableLiveData<MutableList<ImageModel>> by lazy {
        MutableLiveData<MutableList<ImageModel>>()
    }

    private fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            if (savedQuery.isNotEmpty()) {
                val photoResponse = flickrRepository.loadImages(savedQuery, page.value ?: DEFAULT_PAGE)
                val images = photoResponse?.images?.toMutableList() ?: mutableListOf()
                val pageNo = photoResponse?.page ?: DEFAULT_PAGE
                totalPages = photoResponse?.totalPages ?: DEFAULT_PAGE
                page.postValue(pageNo)
                if (photoResponse?.page ?: DEFAULT_PAGE > 1) mImages.postValue(mImages.value?.apply { addAll(images) })
                else mImages.postValue(images)
            }
        }
    }

    fun getImages(query: String? = null): LiveData<MutableList<ImageModel>> {
        query?.takeIf { it != savedQuery }?.let {
            savedQuery = query
            page.value = DEFAULT_PAGE
            mImages.value = mutableListOf()
        } ?: run { page.value = page.value?.plus(1) }
        loadImages()
        return mImages
    }

    fun getPageNumber() = page

}
