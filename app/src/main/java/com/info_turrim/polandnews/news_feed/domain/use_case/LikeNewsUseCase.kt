package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.news_feed.domain.model.LikeNews
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class LikeNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseUseCase<Int, LikeNews>() {
    override suspend fun run(param: Int): Result<LikeNews> {
        return newsRepository.likeNews(param)
    }
}