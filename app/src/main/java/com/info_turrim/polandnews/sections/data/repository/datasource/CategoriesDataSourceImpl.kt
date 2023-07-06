package com.info_turrim.polandnews.sections.data.repository.datasource

import com.info_turrim.polandnews.base.BaseNetworkDataSource
import com.info_turrim.polandnews.common.mapper.CategoryToDomainMapper
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.common.toListMapper
import com.info_turrim.polandnews.sections.data.remote.CategoryApi
import com.info_turrim.polandnews.sections.domain.model.CategoryFollow
import com.info_turrim.polandnews.utils.extension.bodyOrError
import com.info_turrim.polandnews.utils.extension.mapTo
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class CategoriesDataSourceImpl @Inject constructor(
    private val categoryApi: CategoryApi,
    private val categoryToDomainMapper: CategoryToDomainMapper,
) : BaseNetworkDataSource(), CategoriesDataSource {

    override suspend fun getCategories(): Result<List<Category>> {
        return executeWithMapper(categoryToDomainMapper.toListMapper()) {
            categoryApi.getCategories().bodyOrError()
        }
    }

    override suspend fun subscribeForCategory(id: Int): Result<CategoryFollow> {
        return execute {
            val result = categoryApi.subscribeForCategory(id).bodyOrError()
            CategoryFollow(
                results = result.results
            )
        }
    }
}