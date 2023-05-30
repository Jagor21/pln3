package com.info_turrim.polandnews.news_feed.ui.model_view

import android.annotation.SuppressLint
import android.view.View
import com.airbnb.epoxy.*
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.databinding.HolderNewsDetailsSourceBinding
import com.info_turrim.polandnews.utils.extension.EMPTY
import com.info_turrim.polandnews.utils.extension.loadImage

private const val FOLLOWERS_DEFAULT_AMOUNT = "0"

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.holder_news_details_source)
abstract class NewsDetailsSourceModelView : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var sourceUrl: String = String.EMPTY

    @EpoxyAttribute
    var title: String = String.EMPTY

    @EpoxyAttribute
    var followersAmount: String = FOLLOWERS_DEFAULT_AMOUNT

    @EpoxyAttribute
    lateinit var onSourceClick: View.OnClickListener

    override fun bind(holder: DataBindingHolder) {
        (holder.dataBinding as HolderNewsDetailsSourceBinding).let { binding ->
            binding.apply {
                sourceUrl = this@NewsDetailsSourceModelView.sourceUrl
                ivSource.loadImage(this@NewsDetailsSourceModelView.sourceUrl, true)
                title = this@NewsDetailsSourceModelView.title
                followersAmount = this@NewsDetailsSourceModelView.followersAmount
                onSourceClick = this@NewsDetailsSourceModelView.onSourceClick
            }
        }
    }
}