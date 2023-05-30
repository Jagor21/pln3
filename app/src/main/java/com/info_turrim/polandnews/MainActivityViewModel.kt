package com.info_turrim.polandnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.news_feed.data.model.GetAdRequestParam
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.domain.use_case.GetAdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getAdUseCase: GetAdUseCase
) : BaseViewModel() {

    private val _adList = MutableLiveData<List<News>>()
    val adList: LiveData<List<News>>
        get() = _adList

    fun getAd(getAdRequestParam: GetAdRequestParam) {
        viewModelScope.launch {
            getAdUseCase.execute(getAdRequestParam) {
                it.fold(
                    onSuccess = {
                        _adList.value = it
                    },
                    onFailure = {

                    }
                )
            }
        }
    }
}