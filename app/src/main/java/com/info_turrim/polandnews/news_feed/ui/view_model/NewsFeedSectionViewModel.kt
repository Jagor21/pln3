package com.info_turrim.polandnews.news_feed.ui.view_model

import androidx.lifecycle.*
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.data.model.GetForYouNewsRequestParam
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.domain.use_case.AddToFavoriteUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.LikeNewsUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.RemoveFromFavoriteUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.ShareNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.news_feed.data.model.GetAdRequestParam

private const val VISIBLE_THRESHOLD = 3

class NewsFeedSectionViewModel @Inject constructor(
    private val shareNewsUseCase: ShareNewsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val likeNewsUseCase: LikeNewsUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
) : BaseViewModel() {

    @Inject
    lateinit var newsRepository: NewsRepository

    private val _newsParams = MutableLiveData<GetNewsRequestParam>()
    val news: LiveData<Result<Set<News>>> = _newsParams.switchMap { param ->
        liveData {
            val result = newsRepository.getNews(param).asLiveData(Dispatchers.Main)
            emitSource(result)
            loading(false)
        }
    }

    private val _forYouNewsParams = MutableLiveData<GetForYouNewsRequestParam>()
    val forYouNews: LiveData<Result<Set<News>>> = _forYouNewsParams.switchMap { param ->
        liveData {
            val result = newsRepository.getForYouNews(param).asLiveData(Dispatchers.Main)
            emitSource(result)
            loading(false)
        }
    }

    private val _sharedResult = MutableLiveData<String>()
    val sharedResult: LiveData<String>
        get() = _sharedResult

    private val _addToFavoriteResult = MutableLiveData<String?>()
    val addToFavoriteResult: LiveData<String?>
        get() = _addToFavoriteResult

    private val _removeFromFavouriteResult = MutableLiveData<String>()
    val removeFromFavouriteResult: LiveData<String>
        get() = _removeFromFavouriteResult

    fun loadNews(getNewsRequestParam: GetNewsRequestParam) {
        loading(true)
        _newsParams.postValue(getNewsRequestParam)
    }

    fun loadForYouNews(getForYouNewsRequestParam: GetForYouNewsRequestParam) {
        loading(true)
        _forYouNewsParams.postValue(getForYouNewsRequestParam)
    }

    fun listScrolled(
        visibleItemCount: Int,
        lastVisibleItemPosition: Int,
        totalItemCount: Int,
        isForYou: Boolean
    ) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD>= totalItemCount) {
            if (isForYou) {
                _forYouNewsParams.value?.let {
                    viewModelScope.launch {
                        newsRepository.requestMoreForYou(
                            it
                        )
                    }
                }
            } else {
                _newsParams.value?.let { viewModelScope.launch { newsRepository.requestMore(it) } }
            }
        }
    }

    fun onShareNews(id: Int) {
        launchUseCase {
            shareNewsUseCase.execute(id) {
                it.fold(
                    onSuccess = {
                        _sharedResult.value = it.result
                    },
                    onFailure = {
                        it as Result.ErrorResult.NetworkErrorResponse
                        Timber.tag("ERROR").d("${it.code} | ${it.errorMessage}")
                    }
                )
            }
        }
    }

    fun likeNews(id: Int) {
        launchUseCase {
            likeNewsUseCase.execute(id) {
                it.fold(
                    onSuccess = {

                    },
                    onFailure = {

                    }
                )
            }
        }
    }

    fun addToFavorite(favoriteRequest: FavoriteRequest) {
        launchUseCase {
            addToFavoriteUseCase.execute(favoriteRequest) {
                it.fold(
                    onSuccess = {
                        _addToFavoriteResult.value = it.result
                    },
                    onFailure = {}
                )
            }
        }
    }

    fun removeFromFavourite(id: Int) {
        launchUseCase {
            removeFromFavoriteUseCase.execute(id) {
                it.fold(
                    onSuccess = {
                        _removeFromFavouriteResult.value = it.result
                    },
                    onFailure = {}
                )
            }
        }
    }

    fun getAd(getAdRequestParam: GetAdRequestParam) {
        launchUseCase {
            newsRepository.getAd(getAdRequestParam)
        }
    }
}