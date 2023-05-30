package com.info_turrim.polandnews.follow.ui.controller

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.addSectionButton
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.base.ModelViewListener
import com.info_turrim.polandnews.common.EpoxyModelProperty
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.sections.ui.model_view.sectionCategoryModelView
import com.info_turrim.polandnews.utils.getCategoryIcon
import com.info_turrim.polandnews.titleDescriptionFollow
import com.info_turrim.polandnews.titleLeft

class FollowController constructor(val context: Context): EpoxyController() {

    var categoryList by EpoxyModelProperty<List<Category>> { emptyList() }

    var listener: ModelViewListener = {}

    override fun buildModels() {
        
//        titleLeft {
//            id("follow_title_section")
//            titleText(context.getString(R.string.sections))
//        }

//        titleDescriptionFollow {
//            id("title_description_follow")
//            descriptionText(context.getString(R.string.follow_sections_description))
//        }


        categoryList.forEach {  category ->
            sectionCategoryModelView {
                id("${category.id}")
                category(category)
                categoryIcon(getCategoryIcon(category.id))
                onCategoryClick(View.OnClickListener {
                    listener(
                        ModelViewEvent.SectionEvent.SectionClickEvent(
                            category,
                            category.title
                        )
                    )
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

        addSectionButton {
            id("add_section_button")
            onAddSectionClick(View.OnClickListener {
                listener(ModelViewEvent.FollowEvent.OnAddSectionClick)
            })
        }


    }
}