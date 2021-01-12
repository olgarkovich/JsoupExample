package com.example.exchangerates.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class InternetConnection (context: Context) {

    val isOnline = isOnline(context)

    private fun isOnline(context: Context?): Boolean {

        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        }
        return false
    }

    fun getDateTime(): String {
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        return dateFormat.format(currentDate)
    }
}