package com.info_turrim.polandnews.common.services

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {

    @GET("url")
    suspend fun getNews(
        @Path("id") id: String
    ): Response
}