package com.farhanrahman.file_create_on_broadcast.util

import java.text.SimpleDateFormat
import java.time.ZoneOffset.UTC
import java.util.*

object DateUtility {
    const val WITH_SEC_DATE_FORMAT = "dd_MM hh mm ss aa"
    fun getTimeInString(dateFormat: String? = WITH_SEC_DATE_FORMAT, timesInMillis: Long): String? {
        val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timesInMillis
        return formatter.format(calendar.time)
    }
}