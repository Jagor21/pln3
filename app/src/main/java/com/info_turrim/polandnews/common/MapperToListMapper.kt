package com.info_turrim.polandnews.common

private class MapperToListMapper<F, T>(
    val singleMapper: Mapper<F, T>
) : Mapper<List<F>, List<T>> {
    override fun map(from: List<F>): List<T> = from.map(singleMapper::map)
}

private class IndexedMapperToListMapper<F, T>(val singleMapper: IndexedMapper<F, T>) :
    Mapper<List<F>, List<T>> {
    override fun map(from: List<F>): List<T> = from.mapIndexed(singleMapper::map)
}

fun <F, T> Mapper<F, T>.toListMapper(): Mapper<List<F>, List<T>> = MapperToListMapper(this)

fun <F, T> IndexedMapper<F, T>.toListMapper(): Mapper<List<F>, List<T>> =
    IndexedMapperToListMapper(this)