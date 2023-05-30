package com.info_turrim.polandnews.profile.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.news_feed.domain.ProfileRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class GetUserCategoriesUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseUseCase<Unit, List<Category>>() {
    override suspend fun run(param: Unit): Result<List<Category>> {
        return profileRepository.getUserCategories()
    }
}