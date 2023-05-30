package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.news_feed.domain.model.SourceDetails
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class GetSourceDetailsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseUseCase<Int, SourceDetails>() {

    override suspend fun run(param: Int): Result<SourceDetails> {
        return newsRepository.getSourceDetails(param)
    }
}