package com.info_turrim.polandnews.sections.ui.view_model

import androidx.lifecycle.*
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import com.info_turrim.polandnews.news_feed.domain.use_case.AddToFavoriteUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.RemoveFromFavoriteUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.ShareNewsUseCase
import com.info_turrim.polandnews.sections.domain.use_case.SubscribeForCategoryUseCase
import com.info_turrim.polandnews.utils.extension.EMPTY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.news_feed.domain.use_case.LikeNewsUseCase
import javax.inject.Inject

private const val VISIBLE_THRESHOLD = 3

class SectionDetailsViewModel @Inject constructor(
    private val shareNewsUseCase: ShareNewsUseCase,
    private val subscribeForCategoryUseCase: SubscribeForCategoryUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val likeNewsUseCase: LikeNewsUseCase,
) : BaseViewModel() {

    @Inject
    lateinit var newsRepository: NewsRepository

    private val _subscriptionResult = MutableLiveData<Pair<Int, String>>()
    val subscriptionResult: LiveData<Pair<Int, String>>
        get() = _subscriptionResult

    private val _newsParams = MutableLiveData<GetNewsRequestParam>()
    val news: LiveData<Result<Set<News>>> = _newsParams.switchMap { param ->
        liveData {
            val result = newsRepository.getNews(param).asLiveData(Dispatchers.Main)
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

    fun loadNews(getNewsRequestParam: GetNewsRequestParam) {
        loading(true)
        _newsParams.postValue(getNewsRequestParam)
    }

    fun listScrolled(
        visibleItemCount: Int,
        lastVisibleItemPosition: Int,
        totalItemCount: Int
    ) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            _newsParams.value?.let { viewModelScope.launch { newsRepository.requestMore(it) } }
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
}