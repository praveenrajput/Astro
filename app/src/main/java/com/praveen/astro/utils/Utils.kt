package com.praveen.astro.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun getFormattedTime(timeStamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return Instant.ofEpochSecond(timeStamp)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}
