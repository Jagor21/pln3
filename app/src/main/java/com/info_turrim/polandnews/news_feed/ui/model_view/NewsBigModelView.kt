package com.info_turrim.polandnews.news_feed.ui.model_view

import android.annotation.SuppressLint
import android.view.View
import com.airbnb.epoxy.*
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.databinding.HolderNewsBigBinding
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.utils.extension.loadImage


@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.holder_news_big)
abstract class NewsBigModelView : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var news: News? = null

    @EpoxyAttribute
    @JvmField
    var isUserReal: Boolean = false

    @EpoxyAttribute
    lateinit var onLikeClick: View.OnClickListener

    @EpoxyAttribute
    lateinit var onCommentsClick: View.OnClickListener

    @EpoxyAttribute
    lateinit var onShareClick: View.OnClickListener

    @EpoxyAttribute
    lateinit var onNewsClick: View.OnClickListener

    @EpoxyAttribute
    lateinit var onBookmarkClick: View.OnClickListener

    @EpoxyAttribute
    lateinit var onSourceClick: View.OnClickListener


    override fun bind(holder: DataBindingHolder) {
        (holder.dataBinding as HolderNewsBigBinding).let { binding ->
            binding.apply {
                news = this@NewsBigModelView.news
                onLikeClick = this@NewsBigModelView.onLikeClick
                onCommentsClick = this@NewsBigModelView.onCommentsClick
                onShareClick = this@NewsBigModelView.onShareClick
                onNewsClick = this@NewsBigModelView.onNewsClick
                onBookmarkClick = this@NewsBigModelView.onBookmarkClick
                onSourceClick = this@NewsBigModelView.onSourceClick
                ivSource.loadImage(news?.source?.image.orEmpty(), true)
                ivNewsImage.loadImage(news?.image.orEmpty(), false)
                isUserReal = this@NewsBigModelView.isUserReal
            }
        }
    }
}