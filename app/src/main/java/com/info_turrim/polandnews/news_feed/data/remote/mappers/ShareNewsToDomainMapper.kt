package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.data.model.ShareNewsResponse
import com.info_turrim.polandnews.news_feed.domain.model.ShareNews
import javax.inject.Inject

class ShareNewsToDomainMapper @Inject constructor() : Mapper<ShareNewsResponse, ShareNews> {
    override fun map(from: ShareNewsResponse) = ShareNews(result = from.result)
}