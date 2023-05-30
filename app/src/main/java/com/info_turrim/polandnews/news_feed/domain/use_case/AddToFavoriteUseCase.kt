package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.domain.model.AddToFavourite
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class AddToFavoriteUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseUseCase<FavoriteRequest, AddToFavourite>() {
    override suspend fun run(param: FavoriteRequest): Result<AddToFavourite> {
        return newsRepository.addToFavorite(param)
    }
}