package com.info_turrim.polandnews.network

import android.net.*
import com.info_turrim.polandnews.common.network.core.NetworkEvent

internal class NetworkStateImp (callback: (NetworkState, NetworkEvent) -> Unit) : NetworkState {

    private var notify: (NetworkEvent) -> Unit

    init {
        this.notify = { e: NetworkEvent -> callback(this, e) }
    }

    override var isAvailable: Boolean = false
        set(value) {
            val old = field
            val odlIConnected = ConnectivityStateHolder.isConnected
            field = value
            notify(NetworkEvent.AvailabilityEvent(this, old, odlIConnected))
        }

    override var network: Network? = null

    override var linkProperties: LinkProperties? = null
        set(value) {
            val old = field
            field = value
            notify(NetworkEvent.LinkPropertyChangeEvent(this, old))
        }

    override var networkCapabilities: NetworkCapabilities? = null
        set(value) {
            val old = field
            field = value
            notify(NetworkEvent.NetworkCapabilityEvent(this, old))
        }
}