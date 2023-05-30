package com.info_turrim.polandnews.options.domain.model

data class Profile(
    val id: Int,
    val city: String?,
    val country: String?,
    val email: String,
    val gclid: String?,
    val password: String?,
    val sex: Int?,
    val username: String,
    val year_of_birth: Int?
)
