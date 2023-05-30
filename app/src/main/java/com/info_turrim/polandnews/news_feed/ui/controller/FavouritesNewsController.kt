package com.info_turrim.polandnews.news_feed.ui.controller

import android.content.Context
import android.view.View
import android.widget.CheckBox
import com.airbnb.epoxy.EpoxyController
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.base.ModelViewListener
import com.info_turrim.polandnews.common.EpoxyModelProperty
import com.info_turrim.polandnews.news_feed.ui.model_view.newsBigModelView
import com.info_turrim.polandnews.options.domain.model.FavouritesNewsItem

class FavouritesNewsController constructor(val context: Context) : EpoxyController() {

    var newsList by EpoxyModelProperty<List<FavouritesNewsItem>> { emptyList() }
    var isUserReal by EpoxyModelProperty<Boolean> { false }

    var listener: ModelViewListener = {}

    override fun buildModels() {
        newsList.forEachIndexed { index, favouriteNews ->
            newsBigModelView {
                id("${favouriteNews.news.id}")
                news(favouriteNews.news)
                isUserReal(isUserReal)
                onLikeClick(View.OnClickListener {
                    if (isUserReal) {
                        val newsList = newsList.toMutableList()
                        val newsToUpdate = newsList.find { it.news.id == favouriteNews.news.id }
                        val newsIndex = newsList.indexOf(favouriteNews)
                        if (newsToUpdate != null) {
                            val updatedNews = if (favouriteNews.news.likedByUser) {
                                newsToUpdate.news.copy(
                                    liked = favouriteNews.news.liked - 1,
                                    likedByUser = false
                                )
                            } else {
                                newsToUpdate.news.copy(
                                    liked = favouriteNews.news.liked + 1,
                                    likedByUser = true
                                )
                            }

                            newsList.removeAt(newsIndex)
                            newsList.add(
                                newsIndex,
                                FavouritesNewsItem(favouriteNews.id, updatedNews)
                            )
                            this@FavouritesNewsController.newsList = newsList
                        }
                    }
                    listener(ModelViewEvent.NewsEvent.NewsLikeClickEvent(favouriteNews.news.id))
                })
                onCommentsClick(View.OnClickListener {
                    listener(
                        ModelViewEvent.NewsEvent.CommentsClickEvent(
                            favouriteNews.news.id,
                            favouriteNews.news.commented.toString()
                        )
                    )
                })
                onBookmarkClick(View.OnClickListener {
                    it as CheckBox
                    if (!it.isChecked) {
                        listener(ModelViewEvent.NewsEvent.RemoveFromFavouritesClickEvent(favouriteNews.news.id))

                    } else {
                        listener(
                            ModelViewEvent.NewsEvent.AddToFavouritesClickEvent(favouriteNews.news.id)
                        )
                    }
                })
                onShareClick(View.OnClickListener {
                    listener(
                        ModelViewEvent.NewsEvent.ShareClickEvent(
                            favouriteNews.news.id,
                            favouriteNews.news.header
                        )
                    )
                })
                onNewsClick(View.OnClickListener {
                    listener(
                        ModelViewEvent.NewsEvent.NewsClickEvent(
                            favouriteNews.news.id,
                            favouriteNews.news.source?.id ?: -1
                        )
                    )
                })

                onSourceClick(View.OnClickListener {
                    favouriteNews.news.source?.id?.let {
                        listener(ModelViewEvent.NewsEvent.NewsSourceClickEvent(it))
                    }
                })
            }
        }
    }
}