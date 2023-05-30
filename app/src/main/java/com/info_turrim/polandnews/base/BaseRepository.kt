package com.info_turrim.polandnews.base

import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    fun <T> getSafeResultDatabase(call: T): Result<T>

    fun <T> produceFlow(onProduce: () -> Flow<T>): Flow<Result<T>>

}