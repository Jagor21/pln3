package com.info_turrim.polandnews.follow.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.follow.domain.use_case.GetFollowedCategoriesUseCase
import com.info_turrim.polandnews.sections.domain.use_case.SubscribeForCategoryUseCase
import com.info_turrim.polandnews.utils.extension.EMPTY
import com.info_turrim.polandnews.base.Result
import timber.log.Timber
import javax.inject.Inject

class FollowViewModel @Inject constructor(
    private val subscribeForCategoryUseCase: SubscribeForCategoryUseCase,
    private val getFollowedCategoriesUseCase: GetFollowedCategoriesUseCase
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    private val _subscriptionResult = MutableLiveData<Pair<Int, String>>()
    val subscriptionResult: LiveData<Pair<Int, String>>
        get() = _subscriptionResult

    fun getUserCategories() {
        launchUseCase {
            getFollowedCategoriesUseCase.execute(Unit) {
                it.fold(
                    onSuccess = {
                        _categories.value = it
                    },
                    onFailure = {
                        it as Result.ErrorResult.NetworkErrorResponse
                        Timber.tag("ERROR").d("${it.code} | ${it.errorMessage}")
                    }
                )
            }
        }
    }

    fun subscribeForCategory(id: Int) {
        launchUseCase {
            subscribeForCategoryUseCase.execute(id) {
                it.fold(
                    onSuccess = {
                        _subscriptionResult.value = id to it.results
                    },
                    onFailure = {
                        _subscriptionResult.value = id to String.EMPTY
                    }
                )
            }
        }
    }
}