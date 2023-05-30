package com.info_turrim.polandnews.base

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.utils.extension.loadImage


@BindingAdapter("drawableStartCompat")
fun setDrawableStart(view: TextView, @DrawableRes resId: Int) {
    view.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0)
}

@BindingAdapter("commentAuthorImage")
fun setCommentAuthorImage(view: ImageView, authorImageUrl: String) {
    if (authorImageUrl.isEmpty()) {
        view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_profile))
    } else {
        view.loadImage(authorImageUrl, true)
    }
}