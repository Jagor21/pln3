package com.info_turrim.polandnews.options.data.models

import com.google.gson.annotations.SerializedName

data class CommentedNewsResponse(
    @SerializedName("header")
    val header: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)