package com.info_turrim.polandnews.auth.data.model

data class SignUpResponse(
    val accessToken: String?,
    val profile: ProfileResponse?,
    val refreshToken: String?
) {
}