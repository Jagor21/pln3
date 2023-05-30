package com.info_turrim.polandnews.utils.extension

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun createTime(dateString: String?): String {
    if(dateString == null) {
        return ""
    } else {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//        sdf.timeZone = TimeZone.getTimeZone("GMT")
        sdf.isLenient = false
        var ago = String.EMPTY
        try {
            val time: Long = sdf.parse(dateString.replace(".000000Z", "Z")).time
            val now = System.currentTimeMillis()
            ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
                .toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ago
    }
}

fun createTimeForComment(dateString: String?): String {
    dateString?.let {
        val locale = Locale("pl", "PL")
        val formattedDate = dateString.substringBefore(".").plus("Z")
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", locale)
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        var ago = String.EMPTY
        try {
            val time: Long = sdf.parse(formattedDate).time
            val now = System.currentTimeMillis()
            ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
                .toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ago
    }
    return String.EMPTY
}

fun createFakeNewsDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val previousDay = if(day != 1) day - 1 else day
    val month = calendar.get(Calendar.MONTH)
    val hour = calendar.get(Calendar.HOUR)
    val minute = calendar.get(Calendar.MINUTE)
    val second = calendar.get(Calendar.SECOND)


    return "$year-$month-${previousDay}T$hour:$minute:${second}Z"
}