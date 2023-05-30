package com.info_turrim.polandnews.options.data.models

import com.google.gson.annotations.SerializedName

data class SupportResponse(
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("header")
    val header: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String
)