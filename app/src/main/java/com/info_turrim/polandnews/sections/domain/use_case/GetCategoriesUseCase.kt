package com.info_turrim.polandnews.sections.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.sections.domain.CategoryRepository
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseUseCase<Unit, List<Category>>() {
    override suspend fun run(param: Unit): Result<List<Category>> {
        return categoryRepository.getCategories()
    }
}