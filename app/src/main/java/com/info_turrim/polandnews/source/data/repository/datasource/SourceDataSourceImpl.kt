package com.info_turrim.polandnews.source.data.repository.datasource

import com.info_turrim.polandnews.source.data.remote.SourceApi
import javax.inject.Inject

class SourceDataSourceImpl @Inject constructor(
    private val sourceApi: SourceApi
) : SourceDataSource {
}