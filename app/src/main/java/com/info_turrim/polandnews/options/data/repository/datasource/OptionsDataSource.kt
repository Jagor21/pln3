package com.info_turrim.polandnews.options.data.repository.datasource

import com.info_turrim.polandnews.options.data.models.EditProfileRequest
import com.info_turrim.polandnews.options.data.models.SupportRequest
import com.info_turrim.polandnews.options.domain.model.EditProfile
import com.info_turrim.polandnews.options.domain.model.Support
import com.info_turrim.polandnews.base.Result

interface OptionsDataSource {

    suspend fun editProfile(editProfileRequest: EditProfileRequest): Result<EditProfile>
    suspend fun sendQuestion(supportRequest: SupportRequest): Result<Support>
}