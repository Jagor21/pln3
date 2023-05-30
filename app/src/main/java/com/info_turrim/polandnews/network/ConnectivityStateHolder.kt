package com.info_turrim.polandnews.network

import android.app.Application
import android.content.Context
import android.net.*
import com.info_turrim.polandnews.common.network.core.*
import com.info_turrim.polandnews.common.network.core.NetworkCallbackImp
import com.info_turrim.polandnews.network.core.ActivityLifecycleCallbacksImp

object ConnectivityStateHolder : ConnectivityState {

    private val mutableSet: MutableSet<NetworkState> = mutableSetOf()

    override val networkStats: Iterable<NetworkState>
        get() = mutableSet


    private fun networkEventHandler(state: NetworkState, event: NetworkEvent) {
        when (event) {
            is NetworkEvent.AvailabilityEvent -> {
                if (isConnected != event.oldNetworkAvailability) {
                    NetworkEvents.notify(Event.ConnectivityEvent(state.isAvailable))
                }
            }
            else -> {}
        }
    }

    fun Application.registerConnectivityBroadcaster() {
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImp())

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        listOf(
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build(),
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()
        ).forEach {
            val stateHolder = NetworkStateImp { a, b -> networkEventHandler(a, b) }
            mutableSet.add(stateHolder)
            connectivityManager.registerNetworkCallback(it, NetworkCallbackImp(stateHolder))
        }
    }
}