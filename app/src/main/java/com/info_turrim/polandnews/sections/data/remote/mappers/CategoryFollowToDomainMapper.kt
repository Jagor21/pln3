package com.info_turrim.polandnews.sections.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.sections.data.model.CategoryFollowResult
import com.info_turrim.polandnews.sections.domain.model.CategoryFollow
import javax.inject.Inject

class CategoryFollowToDomainMapper @Inject constructor() :
    Mapper<CategoryFollowResult, CategoryFollow> {
    override fun map(from: CategoryFollowResult): CategoryFollow {
        TODO("Not yet implemented")
    }
}