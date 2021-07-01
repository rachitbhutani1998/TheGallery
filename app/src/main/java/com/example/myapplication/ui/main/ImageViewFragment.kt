package com.example.myapplication.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myapplication.R
import com.example.myapplication.util.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.image_view_fragment.*
import kotlinx.android.synthetic.main.image_view_fragment.view.*


@AndroidEntryPoint
class ImageViewFragment : DialogFragment(), RequestListener<Drawable> {

    companion object {

        private const val IMAGE_URL = "image_url"

        fun newInstance(imageUrl: String) = ImageViewFragment().apply {
            arguments = Bundle().apply {
                putString(IMAGE_URL, imageUrl)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.image_view_fragment, null) as ViewGroup
        builder.setView(view)
        val url = arguments?.getString(IMAGE_URL)
        url?.let { view.iv_main_image.loadImage(context, it, this) }
        return builder.create()
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        dismiss()
        return true
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        dialog?.iv_main_image?.setImageDrawable(resource)
        return true
    }

}
