package com.info_turrim.polandnews.options.data.models

data class EditProfileRequest(
    val username: String,
    val email: String,
    val photo: String? = null,
    val city: String = "London"
)