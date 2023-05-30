package com.info_turrim.polandnews.base

import com.info_turrim.polandnews.common.Mapper
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseNetworkDataSource @Inject constructor() {

    suspend fun <F, T> executeWithMapper(mapper: Mapper<F, T>, block: () -> F): Result<T> {
        return try {
            val response = block.invoke()
            mapper.map(response)
            Result.Success(mapper.map(response))
        } catch (e: HttpException) {
//
            var obj: JSONObject? = null
            var errorsPairs: MutableList<Pair<String, String>>? = null
//
//            e.response()?.errorBody()?.string()?.let {
//                obj = JSONObject(it)
//            }
//            obj?.let {
//                val errors = it.getJSONObject("errors")
//                val keys = errors.keys()
//                errorsPairs = mutableListOf()
//                keys.forEach { key ->
//                    errorsPairs!!.add(Pair(key, errors.getString(key)))
//                }
//            }
            Result.ErrorResult.NetworkErrorResponse(
                code = e.code(),
                errorMessage = e.message(),
                errorsList = errorsPairs
            )
        }
    }

    inline fun <T> execute(crossinline block: () -> T): Result<T> {
        return try {
            val response = block.invoke()
            Result.Success(response)
        } catch (e: HttpException) {
            Result.ErrorResult.NetworkErrorResponse(e.code(), e.message())
        }
    }
}