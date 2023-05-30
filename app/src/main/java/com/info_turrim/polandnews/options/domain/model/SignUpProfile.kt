package com.info_turrim.polandnews.options.domain.model

data class SignUpProfile(
    val profile: Profile,
    val accessToken: String,
    val refreshToken: String
)
