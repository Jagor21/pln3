package com.info_turrim.polandnews.options.data.remote.mapper

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.options.data.models.ProfileEntityResponse
import com.info_turrim.polandnews.options.domain.model.Profile
import javax.inject.Inject

class ProfileToDomainMapper @Inject constructor() : Mapper<ProfileEntityResponse, Profile> {

    override fun map(from: ProfileEntityResponse) = Profile(
        id = from.id,
        city = from.city,
        country = from.country,
        email = from.email,
        gclid = from.gclid,
        password = null,
        sex = from.sex,
        username = from.username,
        year_of_birth = from.year_of_birth
    )
}