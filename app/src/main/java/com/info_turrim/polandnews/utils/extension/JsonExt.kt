package com.info_turrim.polandnews.utils.extension

import com.google.gson.GsonBuilder


inline fun <reified T : Any> Any.mapTo(): T =
    GsonBuilder().create().run {
        fromJson(toJson(this@mapTo), T::class.java)
    }