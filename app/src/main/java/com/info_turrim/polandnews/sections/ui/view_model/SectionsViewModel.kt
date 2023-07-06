package com.info_turrim.polandnews.sections.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.*
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.sections.domain.use_case.GetCategoriesUseCase
import com.info_turrim.polandnews.sections.domain.use_case.SubscribeForCategoryUseCase
import com.info_turrim.polandnews.utils.extension.EMPTY
import javax.inject.Inject

class SectionsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val subscribeForCategoryUseCase: SubscribeForCategoryUseCase,
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    private val _subscriptionResult = MutableLiveData<Pair<Int, String>>()
    val subscriptionResult: LiveData<Pair<Int, String>>
        get() = _subscriptionResult

    fun getCategories() {
        launchUseCase(true) {
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

    fun subscribeForCategory(id: Int) {
        launchUseCase {
            subscribeForCategoryUseCase.execute(id) {
                it.fold(
                    onSuccess = { categoryFollow ->
                        _subscriptionResult.value = id to categoryFollow.results
                    },
                    onFailure = {
                        _subscriptionResult.value = id to String.EMPTY
                    }
                )
            }
        }
    }
}