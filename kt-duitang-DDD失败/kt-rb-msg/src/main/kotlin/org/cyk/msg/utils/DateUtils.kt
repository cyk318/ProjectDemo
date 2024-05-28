package org.cyk.msg.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * mysql 中存的 Date
 * mongo 中存的时间戳
 */
object DateUtils {

    fun now(): Long {
        return Date().time
    }

    fun format(time: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(Date(time))
    }

    fun format(time: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(time)
    }

}

