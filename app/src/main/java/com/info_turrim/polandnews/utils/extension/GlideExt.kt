package com.info_turrim.polandnews.utils.extension

import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.core.GlideApp

private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

fun loadNewsSourceImage(view: ImageView, url: String) {

    val roundedCorners15 = RoundedCorners(15.px)
    val options15 = RequestOptions.bitmapTransform(roundedCorners15)

    GlideApp.with(view)
        .load(url)
        .placeholder(R.drawable.ic_app_logo)
        .transition(DrawableTransitionOptions().crossFade(factory))
        .apply(options15)
        .into(view)
}

fun loadNewsImage(view: ImageView, url: String) {

    val roundedCorners10 = RoundedCorners(10.px)
    val options10 = RequestOptions.bitmapTransform(roundedCorners10)

    GlideApp.with(view)
        .load(url)
        .placeholder(R.drawable.news_placeholder)
        .transition(DrawableTransitionOptions().crossFade(factory))
        .apply(options10)
        .into(view)
}

fun ImageView.loadImage(url: String, isNewsSourceImage: Boolean) {

    val roundedCorners10 = GranularRoundedCorners(48f, 48f, 0f, 0f)
    val options10 = RequestOptions.bitmapTransform(roundedCorners10)

    val roundedCorners15 = RoundedCorners(15.px)
    val options15 = RequestOptions.bitmapTransform(roundedCorners15)

    GlideApp.with(this.context)
        .load(url)
        .placeholder(if (isNewsSourceImage) R.drawable.ic_app_logo else R.drawable.news_placeholder)
//        .transition(DrawableTransitionOptions().crossFade(factory))
        .apply(if (isNewsSourceImage) options15 else options10)
        .into(this)
}

