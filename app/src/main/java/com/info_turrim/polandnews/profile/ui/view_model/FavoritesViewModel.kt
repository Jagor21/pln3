package com.info_turrim.polandnews.profile.ui.view_model

import androidx.lifecycle.*
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.domain.ProfileRepository
import com.info_turrim.polandnews.news_feed.domain.use_case.AddToFavoriteUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.LikeNewsUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.RemoveFromFavoriteUseCase
import com.info_turrim.polandnews.news_feed.domain.use_case.ShareNewsUseCase
import com.info_turrim.polandnews.options.domain.model.FavouritesNewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

private const val VISIBLE_THRESHOLD = 1
private const val DEFAULT_PAGE_INDEX = 1

class FavoritesViewModel @Inject constructor(
    private val shareNewsUseCase: ShareNewsUseCase,
    private val likeNewsUseCase: LikeNewsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
) : BaseViewModel() {

    @Inject
    lateinit var profileRepository: ProfileRepository

    private val _sharedResult = MutableLiveData<String>()
    val sharedResult: LiveData<String>
        get() = _sharedResult

    private val _addToFavouriteResult = MutableLiveData<String>()
    val addToFavouriteResult: LiveData<String>
        get() = _addToFavouriteResult

    private val _removeFromFavouriteResult = MutableLiveData<String>()
    val removeFromFavouriteResult: LiveData<String>
        get() = _removeFromFavouriteResult

    private val _newsParams = MutableLiveData<Int>()
    val news: LiveData<Result<List<FavouritesNewsItem>>> = _newsParams.switchMap { param ->
        liveData {
            val result = profileRepository.getFavoritesNews(param).asLiveData(Dispatchers.Main)
            emitSource(result)
            loading(false)
        }
    }

    fun loadNews() {
        loading(true)
        _newsParams.postValue(DEFAULT_PAGE_INDEX)
    }

    fun listScrolled(
        visibleItemCount: Int,
        lastVisibleItemPosition: Int,
        totalItemCount: Int
    ) {
        if (visibleItemCount + lastVisibleItemPosition /*+ VISIBLE_THRESHOLD*/ >= totalItemCount) {
            _newsParams.value?.let { viewModelScope.launch { profileRepository.requestMore(it) } }
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
                        _addToFavouriteResult.value = it.result
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