package com.info_turrim.polandnews.base


import kotlinx.coroutines.flow.Flow

abstract class BaseFlowUseCase<in Param, out T> {

    abstract suspend fun run(param: Param): Flow<T>

    suspend fun execute(param: Param, onCollect: (T) -> Unit) {
        run(param).collect { data ->
            onCollect(data)
        }
    }
//
//    suspend fun execute(param: Param, onCollect: (T) -> Unit) {
//        run(param).collect { data ->
//            onCollect(data)
//        }
//    }

}