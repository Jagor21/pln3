package com.info_turrim.polandnews.news_feed.ui.controller

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.CheckBox
import com.airbnb.epoxy.EpoxyController
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.commentSection
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.base.ModelViewListener
import com.info_turrim.polandnews.common.EpoxyModelProperty
import com.info_turrim.polandnews.news_feed.domain.model.Comment
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.domain.model.NewsDetails
import com.info_turrim.polandnews.news_feed.domain.model.SourceDetails
import com.info_turrim.polandnews.news_feed.ui.model_view.*
import com.info_turrim.polandnews.options.domain.model.FavouritesNewsItem
import com.info_turrim.polandnews.utils.extension.getNumberAbbreviation
import com.info_turrim.polandnews.newsDetailsHeader

class NewsDetailsController constructor(private val context: Context) : EpoxyController() {

    var newsDetails by EpoxyModelProperty<NewsDetails?> { null }
    var favouriteNews by EpoxyModelProperty<List<FavouritesNewsItem>> { emptyList() }
    var sourceDetails by EpoxyModelProperty<SourceDetails?> { null }
    var comment by EpoxyModelProperty<Comment?> { null }
    var newsList by EpoxyModelProperty<List<News>> { emptyList() }
    var isUserReal by EpoxyModelProperty<Boolean> { false }
    var listener: ModelViewListener = {}

    override fun buildModels() {
        newsDetails?.let { newsDetails ->

//            newsDetailsHeader {
//                id("news_details_header")
//                header(newsDetails.header)
//            }

//            newsDetailsSourceModelView {
//                id("news_details_source")
//                sourceUrl(newsDetails.source.image.orEmpty())
//                title(newsDetails.source.title)
//                followersAmount(
//                    context.getString(
//                        R.string.followers_count,
//                        getNumberAbbreviation(sourceDetails?.followerCount?.toDouble() ?: 0.0)
//                    )
//                )
//                onSourceClick(View.OnClickListener {
//                    listener(ModelViewEvent.NewsDetailsEvent.NewsDetailsSourceClickEvent(newsDetails.source.id))
//                })
//            }

            newsDetailsModelView {
                id("news_details_${newsDetails.id}")
                newsDetails(newsDetails)
                onContinueReadingClick(View.OnClickListener {
                    listener(
                        ModelViewEvent.NewsDetailsEvent.NewsDetailsContinueReadingClickEvent(
                            newsDetails.link
                        )
                    )
                })
                onBackClick(View.OnClickListener {
                    listener(ModelViewEvent.NewsDetailsEvent.BackClickEvent)
                })
                onShareClick(View.OnClickListener {
                    listener(
                        ModelViewEvent.NewsDetailsEvent.NewsDetailsShareClickEvent(
                            newsDetails.link,
                            newsDetails.header
                        )
                    )
                })
                onLikeClick(View.OnClickListener {
                    if (isUserReal) {
                        this@NewsDetailsController.newsDetails = if (newsDetails.likedByUser) {
                            newsDetails.copy(liked = newsDetails.liked - 1, likedByUser = false)
                        } else {
                            newsDetails.copy(liked = newsDetails.liked + 1, likedByUser = true)
                        }
                    }

                    listener(ModelViewEvent.NewsDetailsEvent.NewsDetailsLikeClickEvent(newsDetails.id))
                })
                onBookmarkClick(View.OnClickListener {
                    Log.d("onBookmarkClick", "isUserReal $isUserReal")
                    it as CheckBox
                    if (isUserReal) {
                        Log.d("onBookmarkClick", "isUserReal $isUserReal")
                        val favouriteId = favouriteNews.find { it.news.id == newsDetails.id }?.id
                        if (!it.isChecked) {
                            favouriteId?.let {
                                listener(
                                    ModelViewEvent.NewsDetailsEvent.NewsDetailsRemoveFromFavouriteClickEvent(
                                        it
                                    )
                                )
                            }
                        } else {
                            Log.d("onBookmarkClick", "isUserReal $isUserReal")
                            listener(
                                ModelViewEvent.NewsDetailsEvent.NewsDetailsAddToFavouriteClickEvent(
                                    newsDetails.id
                                )
                            )
                        }
                    } else {
                        it.isChecked = false
                        listener(
                            ModelViewEvent.NewsDetailsEvent.NewsDetailsAddToFavouriteClickEvent(
                                -1
                            )
                        )
                    }
                })
            }

            commentSection {
                id("comment_${comment?.id ?: -1}")
                onCommentsClick(View.OnClickListener {
                    listener(
                        ModelViewEvent.NewsDetailsEvent.NewsDetailsCommentsClickEvent(
                            newsDetails.id,
                            newsDetails.commented.toString()
                        )
                    )
                })
                onCommentLike(View.OnClickListener {
                    if (isUserReal) {
                        comment?.let {
                            val updatedComment = if (it.likedByUser) {
                                it.copy(liked = it.liked - 1, likedByUser = false)
                            } else {
                                it.copy(liked = it.liked + 1, likedByUser = true)
                            }
                            comment = updatedComment
                            listener(
                                ModelViewEvent.NewsDetailsEvent.NewsDetailsCommentLikeClickEvent(it.id)
                            )
                        }
                    }
                })
                isCommentVisible(comment != null)
                onLeaveCommentClick(View.OnClickListener {
                    listener(
                        ModelViewEvent.NewsDetailsEvent.NewsDetailsLeaveCommentClickEvent(
                            newsDetails.id
                        )
                    )
                })
                comment(comment)
                commentCount(context.getString(R.string.comment_count, newsDetails.commented))
            }

            newsList.forEach { news ->
                if (news.id != newsDetails.id) {
                    newsBigModelView {
                        id("${news.id}")
                        news(news)
                        onLikeClick(View.OnClickListener {
                            listener(ModelViewEvent.NewsEvent.NewsLikeClickEvent(news.id))
                        })
                        onCommentsClick(View.OnClickListener {
                            listener(
                                ModelViewEvent.NewsEvent.CommentsClickEvent(
                                    news.id,
                                    news.commented.toString()
                                )
                            )
                        })
                        onBookmarkClick(View.OnClickListener {
                            it as CheckBox
                            if (!it.isChecked) {
                                val favouriteId = favouriteNews.find {
                                    it.news.id == news.id
                                }?.id
                                favouriteId?.let {
                                    listener(
                                        ModelViewEvent.NewsEvent.RemoveFromFavouritesClickEvent(
                                            it
                                        )
                                    )
                                }
                            }
                            listener(ModelViewEvent.NewsEvent.AddToFavouritesClickEvent(news.id))
                        })
                        onShareClick(View.OnClickListener {
                            listener(ModelViewEvent.NewsEvent.ShareClickEvent(news.id, news.header))
                        })
                        onNewsClick(View.OnClickListener {
                            listener(
                                ModelViewEvent.NewsEvent.NewsClickEvent(
                                    news.id,
                                    news.source?.id ?: -1
                                )
                            )
                        })
                        onSourceClick(View.OnClickListener {
                            news.source?.id?.let {
                                listener(ModelViewEvent.NewsEvent.NewsSourceClickEvent(it))
                            }
                        })
                    }
                }
            }
        }
    }
}