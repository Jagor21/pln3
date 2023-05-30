package com.info_turrim.polandnews.news_feed.data.remote

import com.info_turrim.polandnews.news_feed.data.model.AdDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AdApi {

    @GET("mobile")
    fun getAds(
        @Query("page_view_uuid") uuid: String,
        @Query("ads_quantity") adsQuantity: Int,
        @Query("language_code") languageCode: String = "pl",
        @Query("country_code") countryCode: String = "PL",
        @Query("stream_id") streamId: Int = 276440,
        @Query("webmaster_id") webmasterId: Int = 30745,
    ): Call<AdDataResponse>
}