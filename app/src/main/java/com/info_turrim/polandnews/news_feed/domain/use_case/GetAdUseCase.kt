package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.news_feed.data.model.GetAdRequestParam
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import javax.inject.Inject

class GetAdUseCase @Inject constructor(
    private val newsRepository: NewsRepository
)/* : BaseUseCase<GetAdRequestParam, Unit>() {

    override suspend fun run(param: GetAdRequestParam): Result<Unit> {
        return newsRepository.getAd(param)
    }
}*/