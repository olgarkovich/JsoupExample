package com.example.exchangerates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.R
import com.example.exchangerates.adapter.CurrencyAdapter
import com.example.exchangerates.model.Currency

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val list = view.findViewById<RecyclerView>(R.id.currencyList)
        val currencyList = seedData()

        val adapter = CurrencyAdapter(requireContext(), currencyList)
        list.adapter = adapter

        val decoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        decoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.color.design_default_color_on_primary)!!)
        list.addItemDecoration(decoration)

        return view
    }

    private fun seedData(): ArrayList<Currency> {
        val currencyList = ArrayList<Currency>()
        for (i in 0..5) {
            val currency = Currency("Доллар", "2.34343", "3.21321","2313133")
            currencyList.add(currency)
        }
        return currencyList
    }
}