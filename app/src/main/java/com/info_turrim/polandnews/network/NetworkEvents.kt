package com.info_turrim.polandnews.network

import androidx.lifecycle.LiveData

object NetworkEvents : LiveData<Event>() {

    internal fun notify(event: Event) {
        postValue(event)
    }

}