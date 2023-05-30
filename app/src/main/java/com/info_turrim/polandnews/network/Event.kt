package com.info_turrim.polandnews.network

sealed class Event {
    class ConnectivityEvent(val isConnected: Boolean) : Event()
}