package com.info_turrim.polandnews.news_feed.ui.model_view

import android.annotation.SuppressLint
import android.view.View
import com.airbnb.epoxy.*
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.databinding.HolderNewsDetailsBinding
import com.info_turrim.polandnews.news_feed.domain.model.NewsDetails
import com.info_turrim.polandnews.utils.extension.loadImage

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.holder_news_details)
abstract class NewsDetailsModelView : DataBindingEpoxyModel() {

    @EpoxyAttribute
    lateinit var onContinueReadingClick: View.OnClickListener

    @EpoxyAttribute
    lateinit var onShareClick: View.OnClickListener

    @EpoxyAttribute
    lateinit var onLikeClick: View.OnClickListener

    @EpoxyAttribute
    lateinit var onBookmarkClick: View.OnClickListener

    @EpoxyAttribute
    lateinit var onBackClick: View.OnClickListener

    @EpoxyAttribute
    var newsDetails: NewsDetails? = null

    override fun bind(holder: DataBindingHolder) {
        (holder.dataBinding as HolderNewsDetailsBinding).let { binding ->
            binding.apply {
                newsDetails = this@NewsDetailsModelView.newsDetails
                ivNewsImage.loadImage(newsDetails?.image.orEmpty(), false)
                ivSource.loadImage(newsDetails?.source?.image.orEmpty(), true)
                onLikeClick = this@NewsDetailsModelView.onLikeClick
                onShareClick = this@NewsDetailsModelView.onShareClick
                onContinueReadingClick = this@NewsDetailsModelView.onContinueReadingClick
                onBookmarkClick = this@NewsDetailsModelView.onBookmarkClick
                onBackClick = this@NewsDetailsModelView.onBackClick
            }
        }
    }
}