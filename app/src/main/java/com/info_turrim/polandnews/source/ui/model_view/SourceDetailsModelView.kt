package com.info_turrim.polandnews.source.ui.model_view

import android.annotation.SuppressLint
import com.airbnb.epoxy.*
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.databinding.HolderSourceDetailsBinding
import com.info_turrim.polandnews.utils.extension.EMPTY
import com.info_turrim.polandnews.utils.extension.loadImage

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.holder_source_details)
abstract class SourceDetailsModelView : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var sourceImageUrl: String = String.EMPTY

    @EpoxyAttribute
    var followersCount: String = String.EMPTY

    @EpoxyAttribute
    var publicationsCount: String = String.EMPTY

    @EpoxyAttribute
    var subtitle: String = String.EMPTY

    @EpoxyAttribute
    var title: String = String.EMPTY

    override fun bind(holder: DataBindingHolder) {
        (holder.dataBinding as HolderSourceDetailsBinding).let { binding ->
            binding.apply {
                sourceImageUrl = this@SourceDetailsModelView.sourceImageUrl
                followersCount = this@SourceDetailsModelView.followersCount
                publicationsCount = this@SourceDetailsModelView.publicationsCount
                subtitle = this@SourceDetailsModelView.subtitle
                title = this@SourceDetailsModelView.title
                ivSource.loadImage(this@SourceDetailsModelView.sourceImageUrl, isNewsSourceImage = true)
            }
        }
    }
}