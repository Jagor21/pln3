package com.info_turrim.polandnews.network

interface NetworkConnectivityListener {

    val shouldBeCalled: Boolean
        get() = true

    val checkOnResume: Boolean
        get() = true

    val isConnected: Boolean
        get() = ConnectivityStateHolder.isConnected

    fun networkConnectivityChanged(event: Event)
}