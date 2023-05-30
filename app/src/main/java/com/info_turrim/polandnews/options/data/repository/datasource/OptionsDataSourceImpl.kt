package com.info_turrim.polandnews.options.data.repository.datasource

import com.info_turrim.polandnews.base.BaseNetworkDataSource
import com.info_turrim.polandnews.options.data.models.EditProfileRequest
import com.info_turrim.polandnews.options.data.models.SupportRequest
import com.info_turrim.polandnews.options.data.remote.OptionsApi
import com.info_turrim.polandnews.options.data.remote.mapper.EditProfileToDomainMapper
import com.info_turrim.polandnews.options.data.remote.mapper.SupportToDomainMapper
import com.info_turrim.polandnews.options.domain.model.EditProfile
import com.info_turrim.polandnews.options.domain.model.Support
import com.info_turrim.polandnews.utils.extension.bodyOrError
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class OptionsDataSourceImpl @Inject constructor(
    private val optionsApi: OptionsApi,
    private val editProfileToDomainMapper: EditProfileToDomainMapper,
    private val supportToDomainMapper: SupportToDomainMapper
) : BaseNetworkDataSource(), OptionsDataSource {

    override suspend fun editProfile(editProfileRequest: EditProfileRequest): Result<EditProfile> {
        return executeWithMapper(editProfileToDomainMapper) {
            optionsApi.editProfile(editProfileRequest).bodyOrError()
        }
    }

    override suspend fun sendQuestion(supportRequest: SupportRequest): Result<Support> {
        return executeWithMapper(supportToDomainMapper) {
            optionsApi.sendQuestion(supportRequest).bodyOrError()
        }
    }
}