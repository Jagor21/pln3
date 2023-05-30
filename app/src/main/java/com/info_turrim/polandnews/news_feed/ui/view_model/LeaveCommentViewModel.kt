package com.info_turrim.polandnews.news_feed.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.news_feed.domain.model.LeaveCommentRequestParams
import com.info_turrim.polandnews.news_feed.domain.use_case.LeaveCommentUseCase
import javax.inject.Inject

class LeaveCommentViewModel @Inject constructor(
    private val leaveCommentUseCase: LeaveCommentUseCase
) : BaseViewModel() {

    private val _leaveCommentResult = MutableLiveData<Boolean>()
    val leaveCommentResult: LiveData<Boolean>
        get() = _leaveCommentResult


    fun leaveComment(leaveCommentRequestParams: LeaveCommentRequestParams) {
        launchUseCase {
            leaveCommentUseCase.execute(leaveCommentRequestParams) {
                it.fold(
                    onSuccess = { _leaveCommentResult.value = true },
                    onFailure = {}
                )
            }
        }
    }
}