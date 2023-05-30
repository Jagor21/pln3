package com.info_turrim.polandnews.news_feed

import java.util.*

enum class NewsFilterType {
    USER_BASED,
    TRENDS,
    GEO_BASED,
    BREAKING,
    LATEST,
    USER_NEWS,
    FOR_YOU("for-you");

    val text: String

    constructor() {
        this.text = name
    }

    constructor(text: String) {
        this.text = text
    }

    override fun toString(): String {
        return text.lowercase(Locale.ROOT)
    }
}