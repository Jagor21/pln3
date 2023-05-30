package com.info_turrim.polandnews.utils.extension

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.showError(string: String) {
    this.apply {
        error = string
        isErrorEnabled = true
    }
}

fun TextInputLayout.hideError() {
    this.apply {
        error = null
        isErrorEnabled = false
    }
}