package com.info_turrim.polandnews.options.data.repository

import com.info_turrim.polandnews.options.data.models.EditProfileRequest
import com.info_turrim.polandnews.options.data.models.SupportRequest
import com.info_turrim.polandnews.options.data.repository.datasource.OptionsDataSource
import com.info_turrim.polandnews.options.domain.OptionsRepository
import com.info_turrim.polandnews.options.domain.model.EditProfile
import com.info_turrim.polandnews.options.domain.model.Support
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class OptionsRepositoryImpl @Inject constructor(
    private val optionsDataSource: OptionsDataSource
) : OptionsRepository {
    override suspend fun editProfile(editProfileRequest: EditProfileRequest): Result<EditProfile> {
        return optionsDataSource.editProfile(editProfileRequest)
    }

    override suspend fun sendQuestion(supportRequest: SupportRequest): Result<Support> {
        return optionsDataSource.sendQuestion(supportRequest)
    }
}