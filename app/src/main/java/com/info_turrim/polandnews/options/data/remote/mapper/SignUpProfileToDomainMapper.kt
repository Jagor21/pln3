package com.info_turrim.polandnews.options.data.remote.mapper

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.options.data.models.SignUpProfileResponse
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import javax.inject.Inject

class SignUpProfileToDomainMapper @Inject constructor(
    private val profileToDomainMapper: ProfileToDomainMapper
) : Mapper<SignUpProfileResponse, SignUpProfile> {
    override fun map(from: SignUpProfileResponse) = SignUpProfile(
        profile = profileToDomainMapper.map(from.profile),
        accessToken = from.accessToken,
        refreshToken = from.refreshToken
    )
}