package com.info_turrim.polandnews.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

abstract class BaseRepositoryImpl : BaseRepository {

    override fun <T> getSafeResultDatabase(call: T): Result<T> {
        return try {
            Result.Success(call)
        } catch (t: Throwable) {
            Result.ErrorResult.LocalErrorResponse(t)
        }
    }

    @ExperimentalCoroutinesApi
    override fun <T> produceFlow(onProduce: () -> Flow<T>): Flow<Result<T>> =
        flow<Result<T>> {
            onProduce().collect {
                emit(Result.Success(it))
            }
        }.catch {
            emit(Result.ErrorResult.LocalErrorResponse(it))
        }.flowOn(Dispatchers.IO)
}