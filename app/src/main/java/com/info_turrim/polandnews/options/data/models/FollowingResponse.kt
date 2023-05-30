package com.info_turrim.polandnews.options.data.models

import com.google.gson.annotations.SerializedName

data class FollowingResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("username")
    val username: String
)