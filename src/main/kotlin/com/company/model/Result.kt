package com.company.model

data class Result(val workout: Workout, val result: Int, var points: Int)

enum class ResultType {
    REPS,
    TIME
}

fun convertSecondsToTime(seconds: Int): String {
    val sec = if (seconds % 60 < 10) "0" + seconds % 60 else (seconds % 60).toString()
    val min = if (seconds / 60 % 60 == 0) "00" else (seconds / 60 % 60).toString()
    val hours = if (seconds / 3600 > 0) (seconds / 3600).toString() + ":" else ""
    return "$hours$min:$sec"

}

fun convertLbToKg(weight: Double): Int {
    return Math.round(weight * 0.4536).toInt()
}

fun convertKgToLb(weight: Double): String {
    return Math.round(weight * 2.2046).toString()
}
