package com.info_turrim.polandnews.sections.ui.model_view

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.DrawableRes
import com.airbnb.epoxy.*
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.core.GlideApp
import com.info_turrim.polandnews.databinding.HolderSectionCategoryBinding
import com.info_turrim.polandnews.utils.extension.loadImage

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.holder_section_category)
abstract class SectionCategoryModelView : DataBindingEpoxyModel() {

    @EpoxyAttribute
    @DrawableRes
    open var categoryIcon: Int = -1

    @EpoxyAttribute
    lateinit var category: Category

    @EpoxyAttribute
    lateinit var onCategoryClick: View.OnClickListener

    @EpoxyAttribute
    lateinit var onSubscribeClick: View.OnClickListener

    override fun bind(holder: DataBindingHolder) {
        (holder.dataBinding as HolderSectionCategoryBinding).let { binding ->
            binding.apply {
                category = this@SectionCategoryModelView.category
                categoryIcon = this@SectionCategoryModelView.categoryIcon
                GlideApp.with(ivCategory).load(this@SectionCategoryModelView.categoryIcon).apply(
                    RequestOptions.bitmapTransform(GranularRoundedCorners(48f, 48f, 48f, 48f))).into(ivCategory)

                onCategoryClick = this@SectionCategoryModelView.onCategoryClick

                onSubscribeClick = this@SectionCategoryModelView.onSubscribeClick
            }
        }
    }


}