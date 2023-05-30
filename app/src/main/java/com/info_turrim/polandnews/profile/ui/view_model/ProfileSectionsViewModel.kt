package com.info_turrim.polandnews.profile.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.profile.domain.use_case.GetUserCategoriesUseCase
import com.info_turrim.polandnews.sections.domain.use_case.SubscribeForCategoryUseCase
import com.info_turrim.polandnews.utils.extension.EMPTY
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class ProfileSectionsViewModel @Inject constructor(
    private val getUserCategoriesUseCase: GetUserCategoriesUseCase,
    private val subscribeForCategoryUseCase: SubscribeForCategoryUseCase,
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<Category>?>()
    val categories: LiveData<List<Category>?>
        get() = _categories

    private val _subscriptionResult = MutableLiveData<Pair<Int, String>>()
    val subscriptionResult: LiveData<Pair<Int, String>>
        get() = _subscriptionResult

    fun getUserCategories() {
        launchUseCase(true) {
            getUserCategoriesUseCase.execute(Unit) {
                it.fold(
                    onSuccess = {
//                        val categoriesList = mutableListOf<Category>()
//                        it.forEach {
//                            if (it.followedByUser)
//                                categoriesList.add(it)
//                        }
                        _categories.value = it
                    },
                    onFailure = {
                        it as Result.ErrorResult.NetworkErrorResponse
                        _categories.value = null
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