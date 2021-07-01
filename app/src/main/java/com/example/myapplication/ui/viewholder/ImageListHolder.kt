package com.example.myapplication.ui.viewholder

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myapplication.R
import com.example.myapplication.data.model.ImageModel
import com.example.myapplication.util.ImageHolderCallback
import com.example.myapplication.util.gone
import com.example.myapplication.util.loadImage
import kotlinx.android.synthetic.main.layout_image_list_holder.view.*

class ImageListHolder constructor(itemView: View, val mCallback: ImageHolderCallback) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(image: ImageModel) {
        itemView.iv_content.loadImage(itemView.context, image, object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                hideView()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                itemView.iv_content.background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_search_et)
                return false
            }

        })
    }

    fun hideView() {
        itemView.gone()
        mCallback.onImageLoadFailed(bindingAdapterPosition)
    }
}