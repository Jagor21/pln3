package com.info_turrim.polandnews.news_feed.data.remote.mappers

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.news_feed.data.model.AdResponse
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.news_feed.domain.model.Source
import com.info_turrim.polandnews.utils.extension.createFakeNewsDate
import javax.inject.Inject
import kotlin.random.Random

class AdToNewsDomainMapper @Inject constructor(): Mapper<AdResponse, News> {

    override fun map(from: AdResponse): News {
        return with(from) {
            News(
                ad = true,
                isAd = true,
                id = from.adId,
                commented = Random.nextInt(7, 100),
                createdAt = createFakeNewsDate(),
                header = from.title,
                image = if (!from.mediaUrl.contains("https")) {
                    "https:${from.mediaUrl}"
                } else {
                    from.mediaUrl
                },
                liked = Random.nextInt(23, 87),
                likedByUser = false,
                shared = Random.nextInt(13, 67),
                source = Source(
                    city = "",
                    country = "",
                    id = -1,
                    image = "",
                    isHidden = false,
                    originalUrl = "",
                    region = "",
                    subtitle = "",
                    title = "News"
                ),
                video = "",
                link = from.clickUrl,
                text = from.title,
                breaking = false,
                likeCount = Random.nextInt(7, 100),
                isFavourites = false,
                sourceUniqueId = "",
                confirmUrl = if (!from.confirmUrl.contains("https")) {
                    "https:${from.confirmUrl}"
                } else {
                    from.confirmUrl
                }
            )
        }
    }
}