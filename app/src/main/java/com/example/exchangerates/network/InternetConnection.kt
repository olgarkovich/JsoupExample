package com.example.exchangerates.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.exchangerates.model.Currency
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object InternetConnection {

    var isOnline = false

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

    fun getDateTime(): String {
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        return dateFormat.format(currentDate)
    }

    fun getCurrency(currencyList: ArrayList<Currency>) {
        try {
            val doc = Jsoup.connect("https://myfin.by/currency/minsk").get()

            val tables: Elements = doc.getElementsByTag("tbody")
            val table: Element = tables[0]
            for (i in 0 until table.childrenSize()) {
                val tableElement = table.child(i)
                val currency = Currency(
                    tableElement.child(0).text(),
                    tableElement.child(1).text(),
                    tableElement.child(2).text(),
                    tableElement.child(3).text()
                )
                currencyList.add(currency)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}