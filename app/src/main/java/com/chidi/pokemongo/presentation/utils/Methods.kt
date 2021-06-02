package com.chidi.pokemongo.presentation.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

private fun currentDate(): Date {
    val calendar: Calendar = Calendar.getInstance()
    return calendar.time
}

fun getTimeAgo(time: Long): String {
    var time = time
    if (time < 1000000000000L) {
        // if timestamp given in seconds, convert to millis
        time *= 1000
    }
    val now: Long = currentDate().time
    if (time > now || time <= 0) {
        return "in the future"
    }
    val diff = now - time
    return if (diff < MINUTE_MILLIS) {
        "Moments ago"
    } else if (diff < 2 * MINUTE_MILLIS) {
        "A minute ago"
    } else if (diff < 50 * MINUTE_MILLIS) {
        (diff / MINUTE_MILLIS).toString() + " minutes ago"
    } else if (diff < 90 * MINUTE_MILLIS) {
        "An hour ago"
    } else if (diff < 24 * HOUR_MILLIS) {
        (diff / HOUR_MILLIS).toString() + " hours ago"
    } else if (diff < 48 * HOUR_MILLIS) {
        "Yesterday"
    } else {
        (diff / DAY_MILLIS).toString() + " days ago"
    }
}


fun timeFormat(time: String): Long {
    val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return try {
        serverDateFormat.parse(time).time
    } catch (e: ParseException) {
        e.printStackTrace()
        3000
    }
}

val dayMonthFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
fun Date.getDMYFormat() = dayMonthFormat.format(this)