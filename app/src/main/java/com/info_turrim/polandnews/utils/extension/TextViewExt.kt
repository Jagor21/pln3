package com.info_turrim.polandnews.utils.extension

import android.widget.TextView
import androidx.core.view.isGone

inline var TextView.textOrGone: CharSequence?
    get() = this.text
    set(value) {
        if (value.isNullOrEmpty()) {
            this.isGone = true
        } else {
            this.text = value
        }
    }