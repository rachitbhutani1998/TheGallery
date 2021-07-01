package com.example.myapplication.util

import com.example.myapplication.data.model.ImageModel

interface ImageHolderCallback {

    fun onImageLoadFailed(pos: Int)

    fun onImageClicked(image: ImageModel)
}