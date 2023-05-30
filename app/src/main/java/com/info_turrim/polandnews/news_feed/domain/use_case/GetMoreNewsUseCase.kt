package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.*
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import javax.inject.Inject

class GetMoreNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseUseCase<GetNewsRequestParam, Unit>() {

    override suspend fun run(param: GetNewsRequestParam): Result<Unit> {
        return Result.Success(newsRepository.requestMore(param))
    }
}