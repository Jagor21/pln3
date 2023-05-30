package com.info_turrim.polandnews.news_feed.ui.controller

import android.content.Context
import com.airbnb.epoxy.EpoxyController

class ForYouNewsController constructor(val context: Context) : EpoxyController() {
//
//    var newsList by EpoxyModelProperty<List<ForYouNews>> { emptyList() }
//    var isUserReal by EpoxyModelProperty<Boolean> { false }
//
//    var listener: ModelViewListener = {}
//
    override fun buildModels() {
//        newsList.forEachIndexed { index, news ->
//            newsBigModelView {
//                id("${news.id}")
//                news(news)
//                isUserReal(isUserReal)
//                onLikeClick(View.OnClickListener {
//                    if (isUserReal) {
//                        val newsList = newsList.toMutableList()
//                        val newsToUpdate = newsList.find { it.id == news.id }
//                        val newsIndex = newsList.indexOf(news)
//                        if (newsToUpdate != null) {
//                            val updatedNews = if (news.likedByUser) {
//                                newsToUpdate.copy(
//                                    liked = news.liked - 1,
//                                    likedByUser = false
//                                )
//                            } else {
//                                newsToUpdate.copy(
//                                    liked = news.liked + 1,
//                                    likedByUser = true
//                                )
//                            }
//
//                            newsList.removeAt(newsIndex)
//                            newsList.add(newsIndex, updatedNews)
//                            this@NewsFeedController.newsList = newsList
//                        }
//                    }
//                    listener(ModelViewEvent.NewsEvent.NewsLikeClickEvent(news.id))
//                })
//                onCommentsClick(View.OnClickListener {
//                    listener(
//                        ModelViewEvent.NewsEvent.CommentsClickEvent(
//                            news.id,
//                            news.commented.toString()
//                        )
//                    )
//                })
//                onBookmarkClick(View.OnClickListener {
//                    listener(ModelViewEvent.NewsEvent.BookmarkClickEvent(news.id))
//                })
//                onShareClick(View.OnClickListener {
//                    listener(ModelViewEvent.NewsEvent.ShareClickEvent(news.id, news.header))
//                })
//                onNewsClick(View.OnClickListener {
//                    listener(
//                        ModelViewEvent.NewsEvent.NewsClickEvent(
//                            news.id,
//                            news.source?.id ?: -1
//                        )
//                    )
//                })
//
//                onSourceClick(View.OnClickListener {
//                    news.source?.id?.let {
//                        listener(ModelViewEvent.NewsEvent.NewsSourceClickEvent(it))
//                    }
//                })
//            }
//        }
    }
}