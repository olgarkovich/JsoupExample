package com.example.exchangerates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.R
import com.example.exchangerates.adapter.CurrencyAdapter
import com.example.exchangerates.model.Currency
import com.example.exchangerates.network.InternetConnection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var list: RecyclerView
    private lateinit var adapter: CurrencyAdapter
    private lateinit var currencyList: ArrayList<Currency>
    private lateinit var progressBar: ProgressBar
    private lateinit var lLayout: LinearLayout
    private lateinit var dateTime: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        if (InternetConnection.isOnline(requireContext())) {
            lLayout = view.findViewById(R.id.linearLayout)
            dateTime = view.findViewById(R.id.dateTime)
            list = view.findViewById(R.id.currencyList)

            init()
        }
        else {
            Toast.makeText(
                requireContext(),
                R.string.no_internet,
                Toast.LENGTH_LONG
            ).show()
            progressBar.visibility = View.GONE
        }

        return view
    }

    private fun init() {
        currencyList = ArrayList()
        adapter = CurrencyAdapter(requireContext(), currencyList)
        list.adapter = adapter

        GlobalScope.launch { getCurrency() }
    }

    private fun getCurrency() {
        InternetConnection.getCurrency(currencyList)
        requireActivity().runOnUiThread { onLoadChange() }
    }

    private fun onLoadChange() {
        lLayout.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        dateTime.text = getString(R.string.date_time, InternetConnection.getDateTime())
        adapter.notifyDataSetChanged()
    }
}