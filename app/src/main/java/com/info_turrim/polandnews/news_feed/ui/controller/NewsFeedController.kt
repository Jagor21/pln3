package com.info_turrim.polandnews.news_feed.ui.controller

import android.content.Context
import android.view.View
import android.widget.CheckBox
import com.airbnb.epoxy.EpoxyController
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.base.ModelViewListener
import com.info_turrim.polandnews.common.EpoxyModelProperty
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.ui.model_view.newsBigModelView
import com.info_turrim.polandnews.options.domain.model.FavouritesNewsItem

class NewsFeedController constructor(val context: Context) : EpoxyController() {

    var newsList by EpoxyModelProperty<MutableList<News>> { mutableListOf() }
    var favouriteNews by EpoxyModelProperty<List<FavouritesNewsItem>> { emptyList() }
    var isUserReal by EpoxyModelProperty<Boolean> { false }

    var listener: ModelViewListener = {}

    override fun buildModels() {
        newsList.forEachIndexed { index, news ->
            if(news.isAd) {
                newsBigModelView {
                    id("${news.id}_${System.currentTimeMillis()}}")
                    news(news)
                    isUserReal(isUserReal)
                    onLikeClick(View.OnClickListener { listener(ModelViewEvent.NewsEvent.AdClickEvent(news.link)) })
                    onCommentsClick(View.OnClickListener { listener(ModelViewEvent.NewsEvent.AdClickEvent(news.link)) })
                    onBookmarkClick(
                        View.OnClickListener { listener(ModelViewEvent.NewsEvent.AdClickEvent(news.link)) })
                    onShareClick(View.OnClickListener {
                        listener(ModelViewEvent.NewsEvent.AdClickEvent(news.link))
                    })
                    onNewsClick(View.OnClickListener {
                        listener(ModelViewEvent.NewsEvent.AdClickEvent(news.link))
                    })

                    onSourceClick(View.OnClickListener {
                        listener(ModelViewEvent.NewsEvent.AdClickEvent(news.link))
                    })
                }
            } else {
                newsBigModelView {
                    id("${news.id}}")
                    news(news)
                    isUserReal(isUserReal)
                    onLikeClick(View.OnClickListener {
                        if (isUserReal) {
                            val newsList = newsList.toMutableList()
                            val newsToUpdate = newsList.find { it.id == news.id }
                            val newsIndex = newsList.indexOf(news)
                            if (newsToUpdate != null) {
                                val updatedNews = if (news.likedByUser) {
                                    newsToUpdate.copy(
                                        liked = news.liked - 1,
                                        likedByUser = false
                                    )
                                } else {
                                    newsToUpdate.copy(
                                        liked = news.liked + 1,
                                        likedByUser = true
                                    )
                                }

                                newsList.removeAt(newsIndex)
                                newsList.add(newsIndex, updatedNews)
                                this@NewsFeedController.newsList = newsList
                            }
                        }
                        listener(ModelViewEvent.NewsEvent.NewsLikeClickEvent(news.id))
                    })
                    onCommentsClick(View.OnClickListener {
                        listener(
                            ModelViewEvent.NewsEvent.CommentsClickEvent(
                                news.id,
                                news.commented.toString(),
                                position = index
                            )
                        )
                    })
                    onBookmarkClick(
                        View.OnClickListener { checkBox ->
                            checkBox as CheckBox
                            if (isUserReal) {
                                val newsList = newsList.toMutableList()
                                val newsToUpdate = newsList.find { it.id == news.id }
                                val newsIndex = newsList.indexOf(news)
                                if (newsToUpdate != null) {
                                    newsList.removeAt(newsIndex)
                                    newsList.add(
                                        newsIndex,
                                        newsToUpdate.copy(isFavourites = !newsToUpdate.isFavourites)
                                    )
                                    this@NewsFeedController.newsList = newsList
                                }
                                if (news.isFavourites) {
                                    listener(
                                        ModelViewEvent.NewsEvent.RemoveFromFavouritesClickEvent(
                                            news.id
                                        )
                                    )
                                } else {
                                    listener(ModelViewEvent.NewsEvent.AddToFavouritesClickEvent(news.id))
                                }
                            }
                        })
                    onShareClick(View.OnClickListener {
                        listener(ModelViewEvent.NewsEvent.ShareClickEvent(news.id, news.header))
                    })
                    onNewsClick(View.OnClickListener {
                        listener(
                            ModelViewEvent.NewsEvent.NewsClickEvent(
                                news.id,
                                news.source?.id ?: -1,
                                position = index
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