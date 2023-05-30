package com.info_turrim.polandnews.options.data.models

import com.google.gson.annotations.SerializedName

data class ProfileEntityResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("gclid")
    val gclid: String?,
    @SerializedName("password")
    val password: String,
    @SerializedName("sex")
    val sex: Int?,
    @SerializedName("username")
    val username: String,
    @SerializedName("year_of_birth")
    val year_of_birth: Int?)
