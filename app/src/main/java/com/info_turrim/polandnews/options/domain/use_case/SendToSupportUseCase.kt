package com.info_turrim.polandnews.options.domain.use_case

import com.info_turrim.polandnews.base.BaseUseCase
import com.info_turrim.polandnews.options.data.models.SupportRequest
import com.info_turrim.polandnews.options.domain.OptionsRepository
import com.info_turrim.polandnews.options.domain.model.Support
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class SendToSupportUseCase @Inject constructor(
    private val optionsRepository: OptionsRepository
) : BaseUseCase<SupportRequest, Support>() {
    override suspend fun run(param: SupportRequest): Result<Support> {
        return optionsRepository.sendQuestion(param)
    }
}