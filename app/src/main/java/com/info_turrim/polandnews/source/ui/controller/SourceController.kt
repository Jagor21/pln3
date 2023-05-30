package com.info_turrim.polandnews.source.ui.controller

import android.view.View
import android.widget.CheckBox
import com.airbnb.epoxy.EpoxyController
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.base.ModelViewListener
import com.info_turrim.polandnews.common.EpoxyModelProperty
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.domain.model.SourceDetails
import com.info_turrim.polandnews.news_feed.ui.model_view.newsBigModelView
import com.info_turrim.polandnews.options.domain.model.FavouritesNewsItem
import com.info_turrim.polandnews.source.ui.model_view.sourceDetailsModelView
import com.info_turrim.polandnews.utils.extension.getNumberAbbreviation
import javax.inject.Inject

class SourceController @Inject constructor() : EpoxyController() {

    var sourceDetails by EpoxyModelProperty<SourceDetails?> { null }
    var newsList by EpoxyModelProperty<List<News>> { emptyList() }
    var favouriteNews by EpoxyModelProperty<List<FavouritesNewsItem>> { emptyList() }
    var isUserLoggedIn by EpoxyModelProperty<Boolean> { false }
    var listener: ModelViewListener = {}

    override fun buildModels() {
        sourceDetails?.let { sourceDetails ->
            sourceDetailsModelView {
                id("source_details${sourceDetails.id}")
                followersCount(getNumberAbbreviation(sourceDetails.followerCount.toDouble()))
                publicationsCount(getNumberAbbreviation(sourceDetails.newsCount.toDouble()))
                subtitle(sourceDetails.subtitle.orEmpty())
                title(sourceDetails.title)
                sourceImageUrl(sourceDetails.image)
            }
        }

        newsList.forEach { news ->
            newsBigModelView {
                id("${news.id}")
                news(news)
                onLikeClick(View.OnClickListener {
                    if (isUserLoggedIn) {
                        val newsList = newsList.toMutableList()
                        val newsToUpdate = newsList.find { it.id == news.id }
                        val newsIndex = newsList.indexOf(news)
                        if (newsToUpdate != null) {
                            val updatedNews = if (news.likedByUser) {
                                newsToUpdate.copy(liked = news.liked - 1, likedByUser = false)
                            } else {
                                newsToUpdate.copy(liked = news.liked + 1, likedByUser = true)
                            }

                            newsList.removeAt(newsIndex)
                            newsList.add(newsIndex, updatedNews)
                            this@SourceController.newsList = newsList
                        }
                    }
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
                    val favouriteId = favouriteNews.find { it.news.id == news.id }?.id
                    if (!it.isChecked) {
                        favouriteId?.let {
                            listener(ModelViewEvent.NewsEvent.RemoveFromFavouritesClickEvent(it))
                        }
                    } else {
                        listener(ModelViewEvent.NewsEvent.AddToFavouritesClickEvent(news.id))
                    }
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
                    news.source?.id?.let { listener(ModelViewEvent.NewsEvent.NewsSourceClickEvent(it)) }
                })
            }
        }
    }
}