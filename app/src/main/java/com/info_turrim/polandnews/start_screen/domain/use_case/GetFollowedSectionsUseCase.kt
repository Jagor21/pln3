package com.info_turrim.polandnews.start_screen.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.start_screen.domain.StartRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class GetFollowedSectionsUseCase @Inject constructor(
    private val startRepository: StartRepository
) : BaseUseCase<Unit, List<Category>>() {
    override suspend fun run(param: Unit): Result<List<Category>> {
        return startRepository.getFollowedSections()
    }
}