package com.info_turrim.polandnews.utils.extension

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import com.info_turrim.polandnews.R
import timber.log.Timber

fun Context.openCustomTab(
    url: String
) {
    val customTab: CustomTabsIntent = CustomTabsIntent.Builder()
        .addDefaultShareMenuItem()
        .setShowTitle(true)
        .build()

    // If we cant find a package name, it means there's no browser that supports
    // Chrome Custom Tabs installed. So, we fallback to the web-view
    if (!isCustomTabsAvailable()) {
        browseLink(url)
    } else {
        try {
            customTab.launchUrl(this, Uri.parse(url))
        } catch (e: Exception) {
            URLUtil.guessUrl(url)
        }
    }
}

@SuppressLint("QueryPermissionsNeeded")
fun Context.isCustomTabsAvailable(): Boolean {

    val viewIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(getString(R.string.test_url))
    )

    if (viewIntent.resolveActivity(packageManager) != null) {
        // Get all apps that can handle VIEW intents.
        val resolvedActivityList = packageManager.queryIntentActivities(viewIntent, 0)
        // packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
        // and service calls.
        val packagesSupportingCustomTabs = mutableListOf<String>()
        resolvedActivityList.forEach { info ->
            val serviceIntent = Intent().apply {
                action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
                setPackage(info.activityInfo.packageName)
            }

            if (packageManager.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName)
            }
        }
        return packagesSupportingCustomTabs.isNotEmpty()
    } else {
        return false
    }
}


fun Context.browseLink(link: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(link)
    )
    try {
        startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        Log.d("browseLink", "No Intent available to handle action")
        Timber.tag("browseLink").d("No Intent available to handle action")
    }
}

