package com.info_turrim.polandnews.options.data.remote.mapper

import com.info_turrim.polandnews.common.Mapper
import com.info_turrim.polandnews.options.data.models.SupportResponse
import com.info_turrim.polandnews.options.domain.model.Support
import javax.inject.Inject

class SupportToDomainMapper @Inject constructor() : Mapper<SupportResponse, Support> {

    override fun map(from: SupportResponse) = Support(
        created_at = from.created_at,
        email = from.email,
        header = from.header,
        id = from.id,
        text = from.text
    )
}