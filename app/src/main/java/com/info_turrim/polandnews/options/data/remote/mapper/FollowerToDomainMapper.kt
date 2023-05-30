package com.info_turrim.polandnews.options.data.remote.mapper

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.options.data.models.FollowerResponse
import com.info_turrim.polandnews.options.domain.model.Follower
import javax.inject.Inject

class FollowerToDomainMapper @Inject constructor() : Mapper<FollowerResponse, Follower> {
    override fun map(from: FollowerResponse) = Follower(
        id = from.id,
        photo = from.photo,
        username = from.username
    )
}