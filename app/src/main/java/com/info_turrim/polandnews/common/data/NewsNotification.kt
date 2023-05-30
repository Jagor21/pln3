package com.info_turrim.polandnews.common.data

import java.util.*

data class NewsNotification(
    val createdAt: Date,
    val header: String,
    val id: Int,
    val image: String?,
    val redirect_information: Redirect,
    val status: String,
    val text: String
)
