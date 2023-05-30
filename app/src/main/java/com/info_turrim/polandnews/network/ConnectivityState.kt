package com.info_turrim.polandnews.network

interface ConnectivityState {
    val isConnected: Boolean
        get() = networkStats.any{
            it.isAvailable
        }

    val networkStats: Iterable<NetworkState>
}