package com.example.exchangerates.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.R
import com.example.exchangerates.adapter.CurrencyAdapter
import com.example.exchangerates.model.Currency
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var doc: Document
    private lateinit var secondThread: Thread
    private lateinit var runnable: Runnable
    private lateinit var list: RecyclerView
    private lateinit var adapter: CurrencyAdapter
    private lateinit var currencyList: ArrayList<Currency>
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        list = view.findViewById(R.id.currencyList)

        init()

        adapter.notifyDataSetChanged()
        list.adapter = adapter

        val decoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        decoration.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.color.design_default_color_on_primary
            )!!
        )
        list.addItemDecoration(decoration)

        return view
    }

    private fun init() {
        currencyList = ArrayList()
        adapter = CurrencyAdapter(requireContext(), currencyList)
        list.adapter

        runnable = Runnable { getCurrency() }
        secondThread = Thread(runnable)
        secondThread.start()

    }

    private fun getCurrency() {
        try {
            doc = Jsoup.connect("https://myfin.by/currency/minsk").get()

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
                    requireActivity().runOnUiThread { progressBar.visibility = View.GONE }
                }
            }
            requireActivity().runOnUiThread { adapter.notifyDataSetChanged() }
        }

        catch (e: IOException) {
            e.printStackTrace()
        }
    }
}