package com.info_turrim.polandnews.news_feed.ui.view_model

import androidx.lifecycle.*
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.news_feed.data.model.GetCommentsRequestParam
import com.info_turrim.polandnews.news_feed.domain.repository.CommentsRepository
import com.info_turrim.polandnews.news_feed.domain.model.Comment
import com.info_turrim.polandnews.news_feed.domain.use_case.LikeCommentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

private const val VISIBLE_THRESHOLD = 3

class NewsCommentsViewModel @Inject constructor(
    private val likeCommentUseCase: LikeCommentUseCase
) : BaseViewModel() {

    @Inject
    lateinit var commentsRepository: CommentsRepository

    private val _commentsParam = MutableLiveData<GetCommentsRequestParam>()
    val comments: LiveData<Result<List<Comment>>> = _commentsParam.switchMap { param ->
        liveData {
            val result = commentsRepository.getCommentsFlow(param).asLiveData(Dispatchers.Main)
            emitSource(result)
        }
    }

    private val _commentLikeResult = MutableLiveData<String>()
    val commentLikeResult: LiveData<String>
        get() = _commentLikeResult

    fun loadComments(getCommentsRequestParam: GetCommentsRequestParam) {
        _commentsParam.postValue(getCommentsRequestParam)
    }

    fun listScrolled(
        visibleItemCount: Int,
        lastVisibleItemPosition: Int,
        totalItemCount: Int
    ) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            _commentsParam.value?.let { viewModelScope.launch { commentsRepository.requestMore(it) } }
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
}