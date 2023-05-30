package com.info_turrim.polandnews.sections.data.repository

import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.sections.data.repository.datasource.CategoriesDataSource
import com.info_turrim.polandnews.sections.domain.CategoryRepository
import com.info_turrim.polandnews.sections.domain.model.CategoryFollow
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class CategoryRepositoryImpl @Inject constructor(
    private val categoriesDataSource: CategoriesDataSource
) : CategoryRepository {

    override suspend fun getCategories(): Result<List<Category>> {
        return categoriesDataSource.getCategories()
    }

    override suspend fun subscribeForCategory(id: Int): Result<CategoryFollow> {
        return categoriesDataSource.subscribeForCategory(id)
    }
}