package com.info_turrim.polandnews.common.model_domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Category(
    val id: Int,
    var followedByUser: Boolean = false,
    val image: String?,
    val title: String,
    val subtitle: String?,
    val sourceCategoryId: Long?,
) : Parcelable {
    val mSubtitle: String?
        get() = subtitle
}
