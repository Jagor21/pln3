package com.info_turrim.polandnews.profile.ui.controller

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.info_turrim.polandnews.addSectionButton
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.base.ModelViewListener
import com.info_turrim.polandnews.common.EpoxyModelProperty
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.sections.ui.model_view.sectionCategoryModelView
import com.info_turrim.polandnews.utils.getCategoryIcon

class ProfileSectionsController constructor(val context: Context) : EpoxyController() {

    var categoryList by EpoxyModelProperty<List<Category>> { emptyList() }

    var listener: ModelViewListener = {}

    override fun buildModels() {
        categoryList.forEach { category ->
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
                        if (category.followedByUser) {
                            ModelViewEvent.ProfileEvent.OnUnsubscribeCategoryClick(category.id)
                        } else {
                            ModelViewEvent.SectionEvent.SectionSubscribeEvent(
                                category.id
                            )
                        }
                    )
                })
            }
        }

        addSectionButton {
            id("profile_sections_add_button")
            onAddSectionClick(View.OnClickListener {
                listener(ModelViewEvent.ProfileEvent.OnAddSectionClick)
            })
        }
    }
}