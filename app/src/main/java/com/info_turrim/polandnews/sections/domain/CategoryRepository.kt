package com.info_turrim.polandnews.sections.domain

import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.sections.domain.model.CategoryFollow
import com.info_turrim.polandnews.base.Result

interface CategoryRepository {
    suspend fun getCategories(): Result<List<Category>>
    suspend fun subscribeForCategory(id: Int): Result<CategoryFollow>
}