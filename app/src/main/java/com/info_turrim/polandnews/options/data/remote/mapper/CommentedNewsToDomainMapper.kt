package com.info_turrim.polandnews.options.data.remote.mapper

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.options.data.models.CommentedNewsResponse
import com.info_turrim.polandnews.options.domain.model.CommentedNews
import javax.inject.Inject

class CommentedNewsToDomainMapper @Inject constructor() : Mapper<CommentedNewsResponse, CommentedNews> {
    override fun map(from: CommentedNewsResponse) = CommentedNews(
        header = from.header,
        id = from.id,
        image = from.image
    )
}