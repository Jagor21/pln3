package com.info_turrim.polandnews.follow.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.follow.domain.repository.FollowRepository
import javax.inject.Inject

class GetFollowedCategoriesUseCase @Inject constructor(
    private val followRepository: FollowRepository
) : BaseUseCase<Unit, List<Category>>() {

    override suspend fun run(param: Unit): Result<List<Category>> {
        return followRepository.getFollowedCategories()
    }
}