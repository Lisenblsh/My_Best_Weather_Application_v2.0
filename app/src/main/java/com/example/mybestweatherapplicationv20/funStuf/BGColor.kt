package com.example.mybestweatherapplicationv20.funStuf

fun getColorForBG (tempFeelsLike: Int) = when (tempFeelsLike) {
    in -50..-31 -> "9fa8da" //indigo
    in -30..-21 -> "90caf9" //blue
    in -20..-11 -> "81d4fa" //light blue
    in -10..0 -> "80deea" //cyan
    in 1..17 -> "80cbc4" //teal
    in 18..24 -> "a5d6a7" //green
    in 25..30 -> "c5e1a5" //light green
    in 31..33 -> "e6ee9c" //lime
    in 34..37 -> "fff59d" //yellow
    in 38..40 -> "ffe082" //amber
    in 41..45 -> "ffcc80" //orange
    in 46..49 -> "ffab91" //deep orange
    50 -> "ef9a9a" //red
    else -> "ffffff" //white
}
