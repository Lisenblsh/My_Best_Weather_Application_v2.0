package com.example.mybestweatherapplicationv20.funStuf

fun getWindDeg(deg: Int) = when (deg) {
    in 0..22 -> "Северный"
    in 23..67 -> "Северо-восточный"
    in 67..112 -> "Восточный"
    in 113..157 -> "Юго-восточный"
    in 158..202 -> "Южный"
    in 203..247 -> "Юго-западный"
    in 248..292 -> "Западный"
    in 293..337 -> "Северо-западный"
    in 338..360 -> "Северный"
    else -> "Ветра нет"
}