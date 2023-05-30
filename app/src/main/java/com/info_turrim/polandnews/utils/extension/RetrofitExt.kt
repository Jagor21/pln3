package com.info_turrim.polandnews.utils.extension

import retrofit2.*

fun <T> Call<T>.bodyOrError(): T {
    return this.execute().bodyOrError()
}

fun <T> Response<T>.bodyOrError(): T {
    if (this.isSuccessful && this.body() != null) {
        return this.body()!!
    }

    throw HttpException(this)
}