package com.info_turrim.polandnews.sections.ui.controller

import android.view.View
import android.widget.CheckBox
import com.airbnb.epoxy.EpoxyController
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.base.ModelViewListener
import com.info_turrim.polandnews.common.EpoxyModelProperty
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.ui.model_view.newsBigModelView
import com.info_turrim.polandnews.options.domain.model.FavouritesNewsItem

class SectionDetailsController : EpoxyController() {

    var sectionNewsList by EpoxyModelProperty<List<News>> { emptyList() }
    var favouriteNews by EpoxyModelProperty<List<FavouritesNewsItem>> { emptyList() }
    var isUserReal by EpoxyModelProperty<Boolean> { false }

    var listener: ModelViewListener = {}

    override fun buildModels() {
        sectionNewsList.forEachIndexed {index, news ->
            newsBigModelView {
                id("news_${news.id}")
                news(news)
                onLikeClick(View.OnClickListener {
                    if (isUserReal) {
                        val newsList = sectionNewsList.toMutableList()
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
                            this@SectionDetailsController.sectionNewsList = newsList
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
                onBookmarkClick(View.OnClickListener {
                    it as CheckBox
//                    if(isUserReal) {
                        val favouriteId = favouriteNews.find { it.news.id == news.id }?.id
                        if (!it.isChecked) {
                            favouriteId?.let {
                                listener(
                                    ModelViewEvent.NewsEvent.RemoveFromFavouritesClickEvent(
                                        favouriteId
                                    )
                                )
                            }
                        } else {
                            listener(ModelViewEvent.NewsEvent.AddToFavouritesClickEvent(news.id))
                        }
//                    } else {
//                        it.isChecked = false
//                        listener(ModelViewEvent.NewsEvent.AddToFavouritesClickEvent(news.id))
//                    }
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
                        listener(ModelViewEvent.NewsEvent.NewsSourceClickEvent(it, position = index))
                    }
                })
            }
        }
    }
}