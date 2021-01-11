package com.example.exchangerates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.R
import com.example.exchangerates.adapter.CurrencyAdapter
import com.example.exchangerates.model.Currency
import com.example.exchangerates.network.InternetConnection
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var list: RecyclerView
    private lateinit var adapter: CurrencyAdapter
    private lateinit var currencyList: ArrayList<Currency>
    private lateinit var progressBar: ProgressBar
    private lateinit var lLayout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val internetConnection = InternetConnection(requireContext())

        if (internetConnection.isOnline) {
            lLayout = view.findViewById(R.id.linearLayout)
            list = view.findViewById(R.id.currencyList)

            init()
        }
        else {
            Toast.makeText(requireContext(), "Отсутствует подключение к интернету", Toast.LENGTH_LONG).show()
            progressBar.visibility = View.GONE
        }

        return view
    }

    private fun init() {
        currencyList = ArrayList()
        adapter = CurrencyAdapter(requireContext(), currencyList)
        list.adapter = adapter

        val runnable = Runnable { getCurrency() }
        val secondThread = Thread(runnable)
        secondThread.start()

    }

    private fun getCurrency() {
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

                if (i == table.childrenSize() - 1) {
                    onLoadChange()
                }
            }
            requireActivity().runOnUiThread { adapter.notifyDataSetChanged() }
        }

        catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun onLoadChange() {
        requireActivity().runOnUiThread {
            lLayout.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}