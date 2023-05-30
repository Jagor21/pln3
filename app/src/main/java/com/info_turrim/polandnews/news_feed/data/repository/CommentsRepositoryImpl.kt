package com.info_turrim.polandnews.news_feed.data.repository

import com.info_turrim.polandnews.common.toListMapper
import com.info_turrim.polandnews.news_feed.data.model.CommentRequest
import com.info_turrim.polandnews.news_feed.data.model.GetCommentsRequestParam
import com.info_turrim.polandnews.news_feed.data.remote.mappers.CommentToDomainMapper
import com.info_turrim.polandnews.news_feed.data.repository.datasource.CommentsDataSource
import com.info_turrim.polandnews.news_feed.domain.repository.CommentsRepository
import com.info_turrim.polandnews.news_feed.domain.model.Comment
import com.info_turrim.polandnews.base.Result
import com.info_turrim.polandnews.news_feed.domain.model.LikeComment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val COMMENTS_STARTING_PAGE_INDEX = 1

class CommentsRepositoryImpl @Inject constructor(
    private val commentsDataSource: CommentsDataSource,
    private val commentToDomainMapper: CommentToDomainMapper
): CommentsRepository {

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<Comment>()

    // shared flow of results, which allows us to broadcast updates so
    // the subscriber will have the latest data
    private val commentsResults = MutableSharedFlow<Result<List<Comment>>>(replay = 1)

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = COMMENTS_STARTING_PAGE_INDEX

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override suspend fun getCommentsFlow(getCommentsRequestParam: GetCommentsRequestParam): Flow<Result<List<Comment>>> {
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestComments(getCommentsRequestParam.copy(pageIndex = lastRequestedPage))
        return commentsResults
    }

    override suspend fun requestMore(getCommentsRequestParam: GetCommentsRequestParam) {
        if (isRequestInProgress) return
        val successful = requestComments(getCommentsRequestParam.copy(pageIndex = lastRequestedPage))
        if (successful) {
            lastRequestedPage++
        }
    }

    override suspend fun onLikeComment(commentId: Int): Result<LikeComment> {
        return commentsDataSource.onLikeComment(commentId)
    }

    override suspend fun getComments(newsId: Int): Result<List<Comment>> {
        return commentsDataSource.getComments(newsId)
    }

    override suspend fun sendComment(commentRequest: CommentRequest, newsId: Int): Result<Comment> {
        return commentsDataSource.sendComment(commentRequest, newsId)
    }

    private suspend fun requestComments(getCommentsRequestParam: GetCommentsRequestParam): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val response =
                commentsDataSource.getCommentsForFlow(getCommentsRequestParam.copy(pageIndex = lastRequestedPage))
            val comments = commentToDomainMapper.toListMapper().map(response)

            inMemoryCache.addAll(comments)
            commentsResults.emit(Result.Success(inMemoryCache))
            successful = true
        } catch (exception: IOException) {
            commentsResults.emit(Result.ErrorResult.LocalErrorResponse(exception))
        } catch (exception: HttpException) {
            commentsResults.emit(
                Result.ErrorResult.NetworkErrorResponse(
                    exception.code(),
                    exception.message()
                )
            )
        }
        isRequestInProgress = false
        return successful
    }
}