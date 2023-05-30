package com.info_turrim.polandnews.common.mapper

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.common.model_data.CategoryResponse
import com.info_turrim.polandnews.common.model_domain.Category
import javax.inject.Inject

class CategoryToDomainMapper @Inject constructor() : Mapper<CategoryResponse, Category> {

    override fun map(from: CategoryResponse) =
        Category(
            id = from.id,
            followedByUser = from.followedByUser,
            image = from.image,
            title = from.title,
            subtitle = from.subtitle,
            sourceCategoryId = from.sourceCategoryId,
        )
}