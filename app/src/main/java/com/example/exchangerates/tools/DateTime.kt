package com.example.exchangerates.tools

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateTime {
    fun getDateTime(): String {
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        return dateFormat.format(currentDate)
    }
}