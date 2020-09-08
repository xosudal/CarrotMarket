package com.study.carrotmarket.util

import java.util.*

fun Date.toHumanReadable(): String {
    val now = Date()
    val diff = now.time - time
    val diffSeconds = diff / 1000 % 60
    val diffMinutes = diff / (60 * 1000) % 60
    val diffHours = diff / (60 * 60 * 1000)
    val diffDays = diff / (60 * 60 * 1000) * 24
    val zerodiff = 0.toLong()

    if(diffSeconds != zerodiff &&
                diffMinutes == zerodiff &&
                diffHours == zerodiff &&
                diffDays == zerodiff)
        return "${diffSeconds} 초 전"

    if(diffMinutes != zerodiff &&
        diffHours == zerodiff &&
        diffDays == zerodiff)
        return "${diffMinutes}분 전"

    if(diffHours != zerodiff &&
        diffDays == zerodiff)
        return "${diffHours}시간 전"

    if(diffDays != zerodiff)
        return "${diffDays}일 전"

    return "방금"
}