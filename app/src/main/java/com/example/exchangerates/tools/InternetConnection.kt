package com.example.exchangerates.tools

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.exchangerates.model.Bank
import com.example.exchangerates.model.Currency
import com.example.exchangerates.model.CurrencyPair
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object InternetConnection {

    fun isOnline(context: Context?): Boolean {

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
}