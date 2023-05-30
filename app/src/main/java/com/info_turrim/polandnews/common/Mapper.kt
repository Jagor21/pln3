package com.info_turrim.polandnews.common

interface Mapper<F, out T> {
    fun map(from: F): T
}

interface IndexedMapper<F, T> {
    fun map(index: Int, from: F): T
}

interface Mapper3Source<F1, F2, F3, T> {
    fun map(from1: F1, from2: F2, from3: F3): T
}

interface KeyMapper<F, K, out T> {
    fun map(key: K, from: F): T
}
