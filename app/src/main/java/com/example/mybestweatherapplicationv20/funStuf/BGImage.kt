package com.example.mybestweatherapplicationv20.funStuf

fun getImageForBG(cityTimeZone: Int, sunriseInt: Long, sunsetInt:Long) :String {
    return if (getTimeStringForImage(cityTimeZone) in getTimeSunriseSunsetForImage(sunriseInt, cityTimeZone) until getTimeSunriseSunsetForImage(sunsetInt, cityTimeZone)){
        "d"
    } else {
        "n"
    }
}