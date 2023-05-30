package com.info_turrim.polandnews.options.data.models

import com.google.gson.annotations.SerializedName

data class SignUpProfileResponse(
    @SerializedName("profile")
    val profile: ProfileEntityResponse,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
