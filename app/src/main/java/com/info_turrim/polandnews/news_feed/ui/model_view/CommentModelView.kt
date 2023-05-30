package com.info_turrim.polandnews.news_feed.ui.model_view

import android.annotation.SuppressLint
import android.view.View
import com.airbnb.epoxy.*
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.databinding.HolderNewsCommentBinding
import com.info_turrim.polandnews.news_feed.domain.model.Comment

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.holder_news_comment)
abstract class CommentModelView : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var comment: Comment? = null

    @EpoxyAttribute
    lateinit var onCommentLike: View.OnClickListener


    override fun bind(holder: DataBindingHolder) {
        (holder.dataBinding as HolderNewsCommentBinding).let { binding ->
            binding.apply {
                comment = this@CommentModelView.comment
                onCommentLike = this@CommentModelView.onCommentLike
//                ivAuthorPhoto.loadImage(comment!!.author.photo.orEmpty(), true)
            }
        }
    }
}