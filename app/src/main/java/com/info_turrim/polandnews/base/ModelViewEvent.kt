package com.info_turrim.polandnews.base

import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.options.ui.OptionsType

typealias ModelViewListener = (ModelViewEvent) -> Unit

sealed class ModelViewEvent {

    sealed class SectionEvent : ModelViewEvent() {
        class SectionClickEvent(val category: Category, val sectionName: String) : SectionEvent()
        class SectionSubscribeEvent(val id: Int) : SectionEvent()
    }

    sealed class NewsEvent : ModelViewEvent() {
        class NewsLikeClickEvent(val id: Int, val position: Int = -1) : NewsEvent()
        class CommentsClickEvent(val id: Int, val commentsAmount: String, val position: Int = -1) : NewsEvent()
        class ShareClickEvent(val id: Int, val newsHeader: String, val newsLink: String = "") : NewsEvent()
        class AddToFavouritesClickEvent(val newsId: Int, val position: Int = -1) : NewsEvent()
        class RemoveFromFavouritesClickEvent(val id: Int) : NewsEvent()
        class NewsClickEvent(val id: Int, val sourceId: Int, val position: Int = -1) : NewsEvent()
        class NewsSourceClickEvent(val sourceId: Int, val position: Int = -1) : NewsEvent()
        class AdClickEvent(val adUrl: String) : NewsEvent()
    }

    sealed class CommentEvent : ModelViewEvent() {
        class CommentLikeEvent(val commentId: Int) : CommentEvent()
    }

    sealed class NewsDetailsEvent : ModelViewEvent() {
        class NewsDetailsSourceClickEvent(val sourceId: Int) : NewsDetailsEvent()
        class NewsDetailsContinueReadingClickEvent(val link: String?) : NewsDetailsEvent()
        class NewsDetailsShareClickEvent(val newsLink: String?, val newsHeader: String) : NewsDetailsEvent()
        class NewsDetailsLikeClickEvent(val newsId: Int) : NewsDetailsEvent()
        class NewsDetailsCommentsClickEvent(val newsId: Int, val commentsAmount: String) : NewsDetailsEvent()
        class NewsDetailsCommentLikeClickEvent(val commentId: Int) : NewsDetailsEvent()
        class NewsDetailsLeaveCommentClickEvent(val newsId: Int) : NewsDetailsEvent()
        class NewsDetailsAddToFavouriteClickEvent(val newsId: Int) : NewsDetailsEvent()
        class NewsDetailsRemoveFromFavouriteClickEvent(val id: Int) : NewsDetailsEvent()
        object BackClickEvent : NewsDetailsEvent()
    }

    sealed class FollowEvent : ModelViewEvent() {
        object OnAddSectionClick : FollowEvent()
    }

    sealed class ProfileEvent : ModelViewEvent() {
        object OnAddSectionClick : ProfileEvent()
        class OnUnsubscribeCategoryClick(val categoryId: Int) : ProfileEvent()
    }

    sealed class OptionsEvent : ModelViewEvent() {
        class OnOptionClick(val optionType: OptionsType) : OptionsEvent()
        object OnLogOutClick : OptionsEvent()
    }
}