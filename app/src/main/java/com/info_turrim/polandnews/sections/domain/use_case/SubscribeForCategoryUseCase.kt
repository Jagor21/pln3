package com.info_turrim.polandnews.sections.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.sections.domain.CategoryRepository
import com.info_turrim.polandnews.sections.domain.model.CategoryFollow
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class SubscribeForCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseUseCase<Int, CategoryFollow>() {
    override suspend fun run(param: Int): Result<CategoryFollow> {
        return categoryRepository.subscribeForCategory(param)
    }
}