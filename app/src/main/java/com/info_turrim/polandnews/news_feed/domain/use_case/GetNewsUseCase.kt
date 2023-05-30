package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.*
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import com.info_turrim.polandnews.news_feed.domain.model.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseFlowUseCase<GetNewsRequestParam, Result<Set<News>>>() {

    override suspend fun run(param: GetNewsRequestParam): Flow<Result<Set<News>>> {
        return newsRepository.getNews(param)
    }
}