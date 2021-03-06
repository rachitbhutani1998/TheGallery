package com.example.myapplication.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.example.myapplication.data.model.ImageModel

fun ImageView.loadImage(
    context: Context,
    image: ImageModel,
    glideRequestListener: RequestListener<Drawable>? = null
) {
    val url = image.buildUrl()
    Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.DATA)
        .listener(glideRequestListener).into(this)
}

fun ImageView.loadImage(context: Context?, url: String, glideRequestListener: RequestListener<Drawable>? = null) {
    context?.let {
        Glide.with(it).load(url).listener(glideRequestListener).diskCacheStrategy(DiskCacheStrategy.DATA).into(this)
    }
}

fun ImageModel.buildUrl(): String {
    return String.format("https://farm%s.staticflickr.com/%s/%s_%s_m.jpg", farm, server, id, secret)
}

fun View.show() {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.gone() {
    if (visibility != View.GONE) visibility = View.GONE
}