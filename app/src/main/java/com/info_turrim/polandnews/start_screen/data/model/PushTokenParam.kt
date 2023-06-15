package com.info_turrim.polandnews.start_screen.data.model

import com.google.gson.annotations.SerializedName

data class PushTokenParam(
    @SerializedName("notification_token")
    val token: String
)