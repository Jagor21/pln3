package com.info_turrim.polandnews.options.data.remote.mapper

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.options.data.models.FollowingResponse
import com.info_turrim.polandnews.options.domain.model.Following
import javax.inject.Inject

class FollowingToDomainMapper @Inject constructor() : Mapper<FollowingResponse, Following> {

    override fun map(from: FollowingResponse) = Following(
        id = from.id,
        photo = from.photo,
        username =  from.username
    )
}