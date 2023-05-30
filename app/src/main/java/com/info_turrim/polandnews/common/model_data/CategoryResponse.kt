package com.info_turrim.polandnews.common.model_data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CategoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("followed_by_user")
    var followedByUser: Boolean = false,
    @SerializedName("image")
    val image: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String?,
    val sourceCategoryId: Long?,
) : Parcelable
