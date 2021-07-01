package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class FlickrResponse(
    @SerializedName("photos") val photos: PhotoResponse? = null,
    @SerializedName("stat") val status: String? = null
)

data class PhotoResponse(
    @SerializedName("page") val page: Int? = -1,
    @SerializedName("pages") val totalPages: Int? = -1,
    @SerializedName("perPage") val perPageCount: Int? = -1,
    @SerializedName("total") val totalImageCount: Int? = -1,
    @SerializedName("photo") val images: List<ImageModel>? = null
)