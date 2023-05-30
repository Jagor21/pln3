package com.info_turrim.polandnews.news_feed.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.sections.domain.use_case.GetCategoriesUseCase
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class NewsFeedViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    fun getCategories() {
        launchUseCase {
            getCategoriesUseCase.execute(Unit) {
                it.fold(
                    onSuccess = {
                        _categories.value = it
                    },
                    onFailure = {
                        it as Result.ErrorResult.NetworkErrorResponse
                    }
                )
            }
        }
    }
}