package com.info_turrim.polandnews.sections.data.repository.datasource

import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.sections.domain.model.CategoryFollow
import com.info_turrim.polandnews.base.Result

interface CategoriesDataSource {

    suspend fun getCategories(): Result<List<Category>>
    suspend fun subscribeForCategory(id: Int): Result<CategoryFollow>
}