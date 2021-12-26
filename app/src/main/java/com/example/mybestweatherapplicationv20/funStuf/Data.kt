package com.example.mybestweatherapplicationv20.funStuf

import java.text.SimpleDateFormat
import java.util.*

val dateUpdate = SimpleDateFormat("d MMMM, HH:mm ", Locale("ru"))
val timeHourMinute = SimpleDateFormat("HH:mm")
val timeForecast = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
val currentTimeZone = GregorianCalendar().timeZone.rawOffset

fun getDateUpdate(dt: Long): String? {
    return try {
        dateUpdate.format(dt * 1000L)
    } catch (e: Exception) {
        e.toString()
    }
}

fun getTimeSunriseSunset(time: Long, cityTimeZone: Int): String? {
    return try {
        timeHourMinute.format(Date(time * 1000L - currentTimeZone + cityTimeZone*1000L))
    } catch (e: Exception) {
        e.toString()
    }
}

fun getTimeStringForImage(cityTimeZone: Int): Long {
    return try {
        Date().time - currentTimeZone + cityTimeZone*1000L
    } catch (e: Exception) {
        0
    }
}

fun getTimeSunriseSunsetForImage(time: Long,cityTimeZone: Int): Long {
    return try {
        time*1000L - currentTimeZone + cityTimeZone*1000L
    } catch (e: Exception) {
        0
    }
}

fun getTimeForecast(time: String, cityTimeZone: Int): String? {
    return try {
        timeHourMinute.format(Date(timeForecast.parse(time).time + cityTimeZone*1000L))
    } catch (e: Exception) {
        e.toString()
    }
}