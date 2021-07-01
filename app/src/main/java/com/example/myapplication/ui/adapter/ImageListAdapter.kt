package com.example.myapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.ImageModel
import com.example.myapplication.ui.viewholder.ImageListHolder
import com.example.myapplication.util.ImageHolderCallback

class ImageListAdapter(private val mContext: Context, private val imageHolderCallback: ImageHolderCallback) : RecyclerView.Adapter<ImageListHolder>() {

    private val mImageList = ArrayList<ImageModel>()

    fun getImages() = mImageList

    fun addToBottom(list: List<ImageModel>, clear: Boolean = false) {
        if (clear) clearAdapter()
        mImageList.addAll(list)
        notifyDataSetChanged()
    }

    private fun clearAdapter() {
        mImageList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.layout_image_list_holder, parent, false)
        return ImageListHolder(view, imageHolderCallback)
    }

    override fun onBindViewHolder(holder: ImageListHolder, position: Int) {
        holder.bindTo(mImageList[position])
    }

    override fun getItemCount(): Int = mImageList.size

    fun removeImage(pos: Int) {
        mImageList.removeAt(pos)
        notifyItemRemoved(pos)
    }

}