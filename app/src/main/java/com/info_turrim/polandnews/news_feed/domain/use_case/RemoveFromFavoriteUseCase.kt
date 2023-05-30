package com.info_turrim.polandnews.news_feed.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.news_feed.domain.model.AddToFavourite
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class RemoveFromFavoriteUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseUseCase<Int, AddToFavourite>() {

    override suspend fun run(param: Int): Result<AddToFavourite> {
        return newsRepository.removeFromFavourites(param)
    }
}