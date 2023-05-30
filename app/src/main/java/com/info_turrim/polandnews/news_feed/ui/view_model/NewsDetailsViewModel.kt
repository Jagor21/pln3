package com.info_turrim.polandnews.news_feed.ui.view_model

import androidx.lifecycle.*
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.news_feed.domain.model.Comment
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.domain.model.NewsDetails
import com.info_turrim.polandnews.news_feed.domain.model.SourceDetails
import com.info_turrim.polandnews.news_feed.data.model.FavoriteRequest
import com.info_turrim.polandnews.news_feed.data.model.GetNewsRequestParam
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import com.info_turrim.polandnews.news_feed.domain.use_case.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.info_turrim.polandnews.base.Result
import timber.log.Timber
import javax.inject.Inject

private const val VISIBLE_THRESHOLD = 3

class NewsDetailsViewModel @Inject constructor(
    private val getNewsDetailsUseCase: GetNewsDetailsUseCase,
    private val getCommentUseCase: GetCommentsUseCase,
    private val likeCommentUseCase: LikeCommentUseCase,
    private val shareNewsUseCase: ShareNewsUseCase,
    private val getSourceDetailsUseCase: GetSourceDetailsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val likeNewsUseCase: LikeNewsUseCase,
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

    private val _newsDetails = MutableLiveData<NewsDetails>()
    val newsDetails: LiveData<NewsDetails>
        get() = _newsDetails

    private val _newsComment = MutableLiveData<List<Comment>>()
    val newsComment: LiveData<List<Comment>>
        get() = _newsComment

    private val _commentLikeResult = MutableLiveData<String>()
    val commentLikeResult: LiveData<String>
        get() = _commentLikeResult

    private val _sharedResult = MutableLiveData<String>()
    val sharedResult: LiveData<String>
        get() = _sharedResult

    private val _sourceDetails = MutableLiveData<SourceDetails>()
    val sourceDetails: LiveData<SourceDetails>
        get() = _sourceDetails

    private val _addToFavoriteResult = MutableLiveData<String>()
    val addToFavoriteResult: LiveData<String>
        get() = _addToFavoriteResult

    private val _removeFromFavouriteResult = MutableLiveData<String>()
    val removeFromFavouriteResult: LiveData<String>
        get() = _removeFromFavouriteResult

    fun getNewsDetails(newsId: Int, getNewsRequestParam: GetNewsRequestParam) {
        launchUseCase(true) {
            getNewsDetailsUseCase.execute(newsId) {
                it.fold(
                    onSuccess = {
                        _newsDetails.value = it
                        getComments(newsId, getNewsRequestParam)
                    },
                    onFailure = {}
                )
            }
        }
    }

    fun getComments(newsId: Int, getNewsRequestParam: GetNewsRequestParam) {
        launchUseCase {
            getCommentUseCase.execute(newsId) {
                it.fold(
                    onSuccess = {
                        _newsComment.value = it
                        loadNews(getNewsRequestParam)
                    },
                    onFailure = {
                        it as Result.ErrorResult.NetworkErrorResponse
                        Timber.tag("ERROR").d("${it.code} | ${it.errorMessage}")
                    }
                )
            }
        }
    }

    fun onCommentLike(commentId: Int) {
        launchUseCase {
            likeCommentUseCase.execute(commentId) {
                it.fold(
                    onSuccess = {
                        _commentLikeResult.value = it.result
                    },
                    onFailure = {
                        it as Result.ErrorResult.NetworkErrorResponse
                        Timber.tag("ERROR").d("${it.code} | ${it.errorMessage}")
                    }
                )
            }
        }
    }

//    fun likeNews(id: Int) {
//        launchUseCase {
//            likeNewsUseCase.execute(id) {
//                it.fold(
//                    onSuccess = {
//
//                    },
//                    onFailure = {
//
//                    }
//                )
//            }
//        }
//    }

    fun loadNews(getNewsRequestParam: GetNewsRequestParam) {
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

    fun getSourceDetails(newsId: Int, getNewsRequestParam: GetNewsRequestParam) {
        getNewsRequestParam.sourceId?.let {
            launchUseCase {
                getSourceDetailsUseCase.execute(it) {
                    it.fold(
                        onSuccess = {
                            _sourceDetails.value = it
                            getNewsDetails(newsId, getNewsRequestParam)
                        },
                        onFailure = {

                        }
                    )
                }
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