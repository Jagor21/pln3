package com.info_turrim.polandnews.options.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.options.data.models.EditProfileRequest
import com.info_turrim.polandnews.options.domain.OptionsRepository
import com.info_turrim.polandnews.options.domain.model.EditProfile
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class EditProfileUseCase @Inject constructor(
    private val optionsRepository: OptionsRepository
) : BaseUseCase<EditProfileRequest, EditProfile>() {

    override suspend fun run(param: EditProfileRequest): Result<EditProfile> {
        return optionsRepository.editProfile(param)
    }
}