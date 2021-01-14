package com.example.exchangerates.network

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

    private const val URL = "https://myfin.by/currency/minsk"

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
            val doc = Jsoup.connect(URL).get()

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

    fun getBank(bankList: ArrayList<Bank>) {
        try {
            val doc = Jsoup.connect(URL).get()

            val tables: Elements = doc.getElementsByTag("tbody")
            val table: Element = tables[1]
            val tableElements = table.children()
            val count = 9

            for (i in 1 until tableElements.size) {

                if(tableElements[i].childrenSize() == count) {
                    val currencyList = ArrayList<CurrencyPair>()

                    for (n in 1 until count step 2) {
                        currencyList.add(
                            CurrencyPair(
                                tableElements[i].child(n).text(),
                                tableElements[i].child(n + 1).text()
                            )
                        )
                    }

                    val bank = Bank(
                        tableElements[i].child(0).text(),
                        currencyList
                    )
                    bankList.add(bank)
                }
            }
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }
}