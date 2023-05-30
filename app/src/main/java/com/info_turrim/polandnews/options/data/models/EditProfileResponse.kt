package com.info_turrim.polandnews.options.data.models

import com.google.gson.annotations.SerializedName

data class EditProfileResponse(
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gclid")
    val gclid: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("password")
    val password: String,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("real")
    val real: Boolean,
    @SerializedName("sex")
    val sex: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("year_of_birth")
    val yearOfBirth: Int

//    @SerializedName("results")
//    val results: List<ProfileEntity>
)
