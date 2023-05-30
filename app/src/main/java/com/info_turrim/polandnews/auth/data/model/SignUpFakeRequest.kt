package com.info_turrim.polandnews.auth.data.model

data class SignUpFakeRequest(
    val city: String,
    val email: String,
    val password: String,
    val username: String,
    val real: Boolean
)