package com.info_turrim.polandnews.news_feed.domain

import com.info_turrim.polandnews.news_feed.data.model.GetAdRequestParam
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.domain.use_case.GetAdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdManager (
    private val getAdUseCase: GetAdUseCase
) {

//    @Inject
//    lateinit var getAdUseCase: GetAdUseCase

    private val _adList = MutableStateFlow<List<News>>(emptyList())
    val adList = _adList.asStateFlow()

    var ads = emptyList<News>()

    fun getAd(getAdRequestParam: GetAdRequestParam) {
        CoroutineScope(Dispatchers.IO).launch {
            getAdUseCase.execute(getAdRequestParam) {
                it.fold(
                    onSuccess = {
                        ads = it
                        _adList.update { it }
                    },
                    onFailure = {

                    }
                )
            }
        }
    }

    fun updateAdList() {
        _adList.update {
            val newList = _adList.value.toMutableList()
            newList.removeAt(0)
            newList
        }
    }

}