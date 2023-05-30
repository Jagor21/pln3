package com.info_turrim.polandnews.news_feed.ui.model_view

import android.annotation.SuppressLint
import com.airbnb.epoxy.*
import com.info_turrim.polandnews.R

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.holder_comment_section)
abstract class NewsDetailsCommentsSectionModelView : DataBindingEpoxyModel() {
//
//    @EpoxyAttribute
//    lateinit var onCommentsClick: View.OnClickListener
//
//    @EpoxyAttribute
//    lateinit var onCommentLike: View.OnClickListener
//
//    @EpoxyAttribute
//    lateinit var onLeaveCommentClick: View.OnClickListener
//
//    @EpoxyAttribute
//    lateinit var comment: Comment
//
//    @EpoxyAttribute
//    var commentCount: String = String.EMPTY
//
//    @EpoxyAttribute
//    var isCommentVisible: Boolean = true
//
//
//    override fun bind(holder: DataBindingHolder) {
//        (holder.dataBinding as HolderCommentSectionBinding).let { binding ->
//            binding.apply {
//                onCommentsClick = this@NewsDetailsCommentsSectionModelView.onCommentsClick
//                onCommentLike = this@NewsDetailsCommentsSectionModelView.onCommentLike
//                onLeaveCommentClick = this@NewsDetailsCommentsSectionModelView.onLeaveCommentClick
//                comment = this@NewsDetailsCommentsSectionModelView.comment
//                commentCount = this@NewsDetailsCommentsSectionModelView.commentCount
//                isCommentVisible = this@NewsDetailsCommentsSectionModelView.isCommentVisible
//            }
//        }
//    }
}