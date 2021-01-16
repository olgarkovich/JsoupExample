package com.example.exchangerates.tools

import android.content.Context

class DateTimeStorage {

    companion object {
        fun setDateTime(context: Context, fileName: String, valueName: String) {
            val dateTime = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
            val editor = dateTime.edit()
            editor.clear()
            editor.putString(valueName, DateTime.getDateTime())
            editor.apply()
        }

        fun getDateTime(context: Context, fileName: String, valueName: String): String? {
            val dateTime = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
            return dateTime.getString(valueName, null)
        }
    }
}