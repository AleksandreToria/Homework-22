package com.example.homework22.presentation.extension

import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun TextView.convertEpochDateToRegularDate(epochTime: Long) {
    val date = Date(epochTime * 1000)
    val sdf = SimpleDateFormat("dd MMMM 'at' h:mm a", Locale.getDefault())
    text = sdf.format(date)
}