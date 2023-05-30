package com.info_turrim.polandnews.sections.ui.controller

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.base.ModelViewListener
import com.info_turrim.polandnews.common.EpoxyModelProperty
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.sections.ui.model_view.sectionCategoryModelView
import com.info_turrim.polandnews.utils.getCategoryIcon
import com.info_turrim.polandnews.titleDescription
import com.info_turrim.polandnews.title

class SectionsController constructor(val context: Context) : EpoxyController() {

    var categoryList by EpoxyModelProperty<List<Category>> { emptyList() }

    var listener: ModelViewListener = {}

    var isSectionStart by EpoxyModelProperty<Boolean> { false }

    override fun buildModels() {
        if (isSectionStart) {
//            title {
//                id("sections_tile")
//                titleText(context.getString(R.string.sections_title))
//            }
//
//            titleDescription {
//                id("titleDescription")
//                descriptionText(context.getString(R.string.sections_description))
//            }
        }

        categoryList.forEach { category ->
            sectionCategoryModelView {
                id("${category.id}")
                category(category)
                categoryIcon(getCategoryIcon(category.id))
                onCategoryClick(View.OnClickListener {

                    if(isSectionStart){
                        listener(
                            ModelViewEvent.SectionEvent.SectionSubscribeEvent(
                                category.id
                            )
                        )
                    } else {
                        listener(
                            ModelViewEvent.SectionEvent.SectionClickEvent(
                                category,
                                category.title
                            )
                        )
                    }
                })
                onSubscribeClick(View.OnClickListener {
                    listener(
                        ModelViewEvent.SectionEvent.SectionSubscribeEvent(
                            category.id
                        )
                    )
                })
            }
        }
    }
}