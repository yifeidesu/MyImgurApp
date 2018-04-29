package com.robyn.myimgurapp.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.robyn.myimgurapp.R

fun loadThumbnailImage(context: Context, imgUrl: String, imageView: ImageView) {

    val options = RequestOptions().fitCenter()
        .placeholder(R.drawable.ic_wait_image)
        .error(R.drawable.ic_wait_image)

    Glide.with(context)
        .load(imgUrl)
        .apply(options)
        .thumbnail(0.1f)
        .into(imageView)
}

fun loadImage(context: Context, imgUrl: String, imageView: ImageView) {

    val options = RequestOptions().centerCrop()
        .placeholder(R.drawable.ic_wait_image)
        .error(R.drawable.ic_wait_image)

    Glide.with(context)
        .load(imgUrl)
        .apply(options)
        .into(imageView)
}

fun makeSnack(view: View, msg: CharSequence, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(view, msg, duration).show()
}

fun showErrorMsg(view: View) {
    val errorMsg = "Network error."
    makeSnack(view, errorMsg)
}

