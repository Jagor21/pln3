package com.info_turrim.polandnews.profile.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.sections.domain.use_case.SubscribeForCategoryUseCase
import com.info_turrim.polandnews.utils.extension.EMPTY
import javax.inject.Inject

class HideSectionBottomSheetDialogViewModel @Inject constructor(
    private val subscribeForCategoryUseCase: SubscribeForCategoryUseCase,
) : BaseViewModel() {

    private val _unsubscribeResult = MutableLiveData<Pair<Int, String>>()
    val unsubscribeResult: LiveData<Pair<Int, String>>
        get() = _unsubscribeResult

    fun onUnsubscribeSection(id: Int) {
        launchUseCase {
            subscribeForCategoryUseCase.execute(id) {
                it.fold(
                    onSuccess = {
                        _unsubscribeResult.value = id to it.results
                    },
                    onFailure = {
                        _unsubscribeResult.value = id to String.EMPTY
                    }
                )
            }
        }
    }
}