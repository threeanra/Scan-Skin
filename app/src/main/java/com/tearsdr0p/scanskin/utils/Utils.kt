package com.tearsdr0p.scanskin.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun convertMillsToDateString(time: Long): String {
    val date = Date(time)
    val dateFormat = SimpleDateFormat("dd MMMM yyyy | HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

