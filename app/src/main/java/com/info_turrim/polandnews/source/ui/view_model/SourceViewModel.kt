package com.info_turrim.polandnews.source.ui.view_model

import androidx.lifecycle.*
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.domain.model.SourceDetails
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import com.info_turrim.polandnews.news_feed.domain.use_case.AddToFavoriteUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.GetSourceDetailsUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.RemoveFromFavoriteUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.ShareNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

private const val VISIBLE_THRESHOLD = 3

class SourceViewModel @Inject constructor(
    private val getSourcesDetailsUseCase: GetSourceDetailsUseCase,
    private val shareNewsUseCase: ShareNewsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
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

    private val _sharedResult = MutableLiveData<String>()
    val sharedResult: LiveData<String>
        get() = _sharedResult

    private val _sourcesDetails = MutableLiveData<SourceDetails>()
    val sourcesDetails: LiveData<SourceDetails>
        get() = _sourcesDetails

    private val _addToFavoriteResult = MutableLiveData<String?>()
    val addToFavoriteResult: LiveData<String?>
        get() = _addToFavoriteResult

    private val _removeFromFavouriteResult = MutableLiveData<String>()
    val removeFromFavouriteResult: LiveData<String>
        get() = _removeFromFavouriteResult

    fun getSourcesDetails(getNewsRequestParam: GetNewsRequestParam) {
        getNewsRequestParam.sourceId?.let{
            launchUseCase {
                getSourcesDetailsUseCase.execute(it) {
                    it.fold(
                        onSuccess = {
                            _sourcesDetails.value = it
                            loadNews(getNewsRequestParam)
                        },
                        onFailure = {

                        }
                    )
                }
            }
        }
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

    fun likeNews(id: Int) {

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

    private fun loadNews(getNewsRequestParam: GetNewsRequestParam) {
        loading(true)
        _newsParams.postValue(getNewsRequestParam)
    }
}