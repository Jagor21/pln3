package com.info_turrim.polandnews.news_feed.ui.controller

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.base.ModelViewListener
import com.info_turrim.polandnews.common.EpoxyModelProperty
import com.info_turrim.polandnews.news_feed.domain.model.Comment
import com.info_turrim.polandnews.news_feed.ui.model_view.commentModelView

class NewsCommentsController constructor(val context: Context) : EpoxyController() {

    var commentList by EpoxyModelProperty<List<Comment>> { emptyList() }
    var isUserLoggedIn by EpoxyModelProperty<Boolean> { false }

    var listener: ModelViewListener = {}

    override fun buildModels() {
        commentList.forEach { comment ->
            commentModelView {
                id("${comment.id}")
                comment(comment)
                onCommentLike(View.OnClickListener {
                    if (isUserLoggedIn){
                        val commentList = commentList.toMutableList()
                        val commentToUpdate = commentList.find { it.id == comment.id }
                        val newsIndex = commentList.indexOf(commentToUpdate)
                        if (commentToUpdate != null) {
                            val updatedComment = if (comment.likedByUser) {
                                commentToUpdate.copy(liked = comment.liked - 1, likedByUser = false)
                            } else {
                                commentToUpdate.copy(liked = comment.liked + 1, likedByUser = true)
                            }

                            commentList.removeAt(newsIndex)
                            commentList.add(newsIndex, updatedComment)
                            this@NewsCommentsController.commentList = commentList
                        }
                    }
                    listener(ModelViewEvent.CommentEvent.CommentLikeEvent(comment.id))
                })
            }
        }
    }
}