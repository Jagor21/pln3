package com.info_turrim.polandnews.network

import android.net.*

interface NetworkState {
    val isAvailable: Boolean

    val network: Network?

    val networkCapabilities: NetworkCapabilities?

    val linkProperties: LinkProperties?

    val isWifi: Boolean
        get() = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false

    val isMobile: Boolean
        get() = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false

    val interfaceName: String?
        get() = linkProperties?.interfaceName
}