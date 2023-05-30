package com.info_turrim.polandnews.common.network.core

import android.net.*
import com.info_turrim.polandnews.network.NetworkStateImp
import timber.log.Timber

internal class NetworkCallbackImp (private val stateHolder: NetworkStateImp) :
    ConnectivityManager.NetworkCallback() {

    private fun updateConnectivity(isAvailable: Boolean, network: Network) {
        stateHolder.network = network
        stateHolder.isAvailable = isAvailable
    }

    override fun onAvailable(network: Network) {
        Timber.tag(TAG).d("[$network] - new network")
        updateConnectivity(true, network)
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        Timber.tag(TAG).d( "[$network] - network capability changed: $networkCapabilities")
        stateHolder.networkCapabilities = networkCapabilities
    }

    override fun onLost(network: Network) {
        Timber.tag(TAG).d( "[$network] - network lost")
        updateConnectivity(false, network)
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        stateHolder.linkProperties = linkProperties
        Timber.tag(TAG).d( "[$network] - link changed: ${linkProperties.interfaceName}")
    }

    override fun onUnavailable() {
        Timber.tag(TAG).d( "Unavailable")
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        Timber.tag(TAG).d( "[$network] - Losing with $maxMsToLive")
    }

    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
        Timber.tag(TAG).d( "[$network] - Blocked status changed: $blocked")
    }

    companion object {
        private const val TAG = "NetworkCallbackImp"
    }
}