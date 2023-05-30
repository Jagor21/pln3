package com.info_turrim.polandnews.options.ui.controller

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.borderlessBtn
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.base.ModelViewListener
import com.info_turrim.polandnews.options.ui.OptionsType
import com.info_turrim.polandnews.itemOptions

class OptionsController constructor(val context: Context) : EpoxyController() {

    var listener: ModelViewListener = {}

    override fun buildModels() {

        itemOptions {
            id("edit_profile")
            optionText(context.getString(R.string.edit_profile))
            optionIcon(R.drawable.ic_option_edit_profile)
            onOptionClick(View.OnClickListener {
                listener(
                    ModelViewEvent.OptionsEvent.OnOptionClick(
                        OptionsType.EDIT_PROFILE
                    )
                )
            })
        }
        itemOptions {
            id("write_support")
            optionText(context.getString(R.string.write_to_support))
            optionIcon(R.drawable.ic_option_write_to_support)
            onOptionClick(View.OnClickListener {
                listener(
                    ModelViewEvent.OptionsEvent.OnOptionClick(
                        OptionsType.WRITE_TO_SUPPORT
                    )
                )
            })
        }
        itemOptions {
            id("app_rating")
            optionText(context.getString(R.string.app_rating))
            optionIcon(R.drawable.ic_option_app_rating)
            onOptionClick(View.OnClickListener {
                listener(
                    ModelViewEvent.OptionsEvent.OnOptionClick(
                        OptionsType.APP_RATING
                    )
                )
            })
        }
        itemOptions {
            id("share_app")
            optionText(context.getString(R.string.share_app))
            optionIcon(R.drawable.ic_option_share_app)
            onOptionClick(View.OnClickListener {
                listener(
                    ModelViewEvent.OptionsEvent.OnOptionClick(
                        OptionsType.SHARE_APP
                    )
                )
            })
        }
        itemOptions {
            id("privacy_policy")
            optionText(context.getString(R.string.privacy_policy))
            optionIcon(R.drawable.ic_option_privacy_policy)
            onOptionClick(View.OnClickListener {
                listener(
                    ModelViewEvent.OptionsEvent.OnOptionClick(
                        OptionsType.PRIVACY_POLICY
                    )
                )
            })
        }
        itemOptions {
            id("about")
            optionText(context.getString(R.string.about))
            optionIcon(R.drawable.ic_option_about)
            onOptionClick(View.OnClickListener {
                listener(
                    ModelViewEvent.OptionsEvent.OnOptionClick(
                        OptionsType.ABOUT
                    )
                )
            })
        }
        itemOptions {
            id("terms_of_use")
            optionText(context.getString(R.string.terms_of_use))
            optionIcon(R.drawable.ic_option_terms_of_use)
            onOptionClick(View.OnClickListener {
                listener(
                    ModelViewEvent.OptionsEvent.OnOptionClick(
                        OptionsType.TERMS_OF_USE
                    )
                )
            })
        }

        borderlessBtn {
            id("log_out_btn")
            btnText(context.getString(R.string.log_out))
            onButtonClick( View.OnClickListener {
                listener(ModelViewEvent.OptionsEvent.OnLogOutClick)
            })
        }

    }
}