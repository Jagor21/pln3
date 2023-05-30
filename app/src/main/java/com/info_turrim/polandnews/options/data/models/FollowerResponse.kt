package com.info_turrim.polandnews.options.data.models

import com.google.gson.annotations.SerializedName

data class FollowerResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("username")
    val username: String
)
