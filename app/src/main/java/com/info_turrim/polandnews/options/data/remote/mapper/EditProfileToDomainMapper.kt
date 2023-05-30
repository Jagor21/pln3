package com.info_turrim.polandnews.options.data.remote.mapper

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.options.data.models.EditProfileResponse
import com.info_turrim.polandnews.options.domain.model.EditProfile
import javax.inject.Inject

class EditProfileToDomainMapper @Inject constructor(
    private val followingToDomainMapper: FollowingToDomainMapper,
    private val followerToDomainMapper: FollowerToDomainMapper,
    private val commentedNewsToDomainMapper: CommentedNewsToDomainMapper
) : Mapper<EditProfileResponse, EditProfile> {

    override fun map(from: EditProfileResponse) = EditProfile(
        city = from.city,
        createdAt = from.createdAt,
        email = from.email,
        id = from.id,
        password = from.password,
        photo = from.photo,
        real = from.real,
        username = from.username,
        country = from.country,
        gclid = from.gclid,
        sex = from.sex,
        yearOfBirth = from.yearOfBirth
    )
}