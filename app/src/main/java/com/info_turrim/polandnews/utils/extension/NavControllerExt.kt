package com.info_turrim.polandnews.utils.extension

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController

fun NavController.saveStateAndNavigate(destinationId: Int, bundle: Bundle = bundleOf(), block:()->Unit = {}) {
    block()
    navigate(destinationId, bundle)
}