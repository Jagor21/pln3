package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import com.info_turrim.polandnews.news_feed.domain.model.ShareNews
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class ShareNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseUseCase<Int, ShareNews>() {

    override suspend fun run(param: Int): Result<ShareNews> {
        return newsRepository.shareNews(param)
    }
}