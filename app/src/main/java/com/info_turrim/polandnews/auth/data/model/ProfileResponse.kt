package com.info_turrim.polandnews.auth.data.model

data class ProfileResponse(
    val city: String,
    val createdAt: String,
    var email: String,
    val id: Long,
    val photo: String?,
    var username: String,
    var real: Boolean,
    val self: Boolean = false
)