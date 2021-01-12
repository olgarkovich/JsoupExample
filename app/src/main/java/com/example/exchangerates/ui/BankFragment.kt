package com.example.exchangerates.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.R
import com.example.exchangerates.adapter.BankAdapter
import com.example.exchangerates.model.Bank
import com.example.exchangerates.model.Currency
import com.example.exchangerates.model.CurrencyPair
import com.example.exchangerates.network.InternetConnection
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException

class BankFragment : Fragment() {

    private lateinit var list: RecyclerView
    private lateinit var adapter: BankAdapter
    private lateinit var bankList: ArrayList<Bank>
    private lateinit var progressBar: ProgressBar
    private lateinit var lLayout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_bank, container, false)
        progressBar = view.findViewById(R.id.progressBarBank)
        progressBar.visibility = View.VISIBLE

        val internetConnection = InternetConnection(requireContext())

        if (internetConnection.isOnline) {
            lLayout = view.findViewById(R.id.linearLayoutBank)
            list = view.findViewById(R.id.bankList)

            init()
        }
        else {
            Toast.makeText(requireContext(), "Отсутствует подключение к интернету", Toast.LENGTH_LONG).show()
            progressBar.visibility = View.GONE
        }

        return view
    }

    private fun init() {
        bankList = ArrayList()
        adapter = BankAdapter(requireContext(), bankList)
        list.adapter = adapter

        val runnable = Runnable { getCurrency() }
        val secondThread = Thread(runnable)
        secondThread.start()

    }

    private fun getCurrency() {
        try {
            val doc = Jsoup.connect("https://myfin.by/currency/minsk").get()

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
                if (i == tableElements.size - 1) {
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
