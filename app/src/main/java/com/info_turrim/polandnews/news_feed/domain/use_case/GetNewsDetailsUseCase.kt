package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.news_feed.domain.model.NewsDetails
import com.info_turrim.polandnews.news_feed.domain.repository.NewsDetailsRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class GetNewsDetailsUseCase @Inject constructor(
    private val newsDetailsRepository: NewsDetailsRepository
) : BaseUseCase<Int, NewsDetails>() {

    override suspend fun run(param: Int): Result<NewsDetails> {
        return newsDetailsRepository.getNewsDetails(param)
    }

}