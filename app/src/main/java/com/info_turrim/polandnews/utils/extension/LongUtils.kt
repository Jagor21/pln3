package com.info_turrim.polandnews.utils.extension

import kotlin.math.round

private val RANGE = 0.0..1000.0

fun getNumberAbbreviation(count: Double): String {

    val thousand = count / 1000
    val million = count / 1000000
    val billion = count / 1000000000

    return when (count) {
        in RANGE -> {
            count.toInt().toString()
        }
        else -> {
            when {
                billion >= 1 -> "${(round(billion * 10) / 10).toInt()}b"
                million >= 1 -> "${(round(million * 10) / 10).toInt()}m"
                thousand >= 1 -> "${(round(thousand * 10) / 10).toInt()}k"
                else -> "0"
            }
        }
    }
}