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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.exchangerates.R
import com.example.exchangerates.adapter.CurrencyAdapter
import com.example.exchangerates.model.Currency
import com.example.exchangerates.repository.CurrencyRepository
import com.example.exchangerates.tools.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var list: RecyclerView
    private lateinit var adapter: CurrencyAdapter
    private lateinit var currencyList: ArrayList<Currency>
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var repository: CurrencyRepository
    private lateinit var progressBar: ProgressBar
    private lateinit var lLayout: LinearLayout
    private lateinit var dateTime: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        swipeRefresh = view.findViewById(R.id.currencyRefresh)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        lLayout = view.findViewById(R.id.linearLayout)
        dateTime = view.findViewById(R.id.dateTime)
        list = view.findViewById(R.id.currencyList)

        init()

        if (!InternetConnection.isOnline(requireContext())) {
            Toast.makeText(
                requireContext(),
                R.string.no_internet,
                Toast.LENGTH_LONG
            ).show()
        }
        return view
    }

    private fun init() {
        repository = CurrencyRepository(requireContext(), GlobalScope)

        currencyList = ArrayList()
        adapter = CurrencyAdapter(requireContext(), currencyList)
        list.adapter = adapter

        GlobalScope.launch { getCurrency() }
    }

    private fun getCurrency() {
        repository.loadAll(currencyList)
        val date =  DateTimeStorage.getDateTime(
            requireContext(), CURRENCY_DATE_TIME, DATE_TIME_VALUE)
        requireActivity().runOnUiThread { onLoadChange(date) }
    }

    private fun onLoadChange(date: String?) {
        if (InternetConnection.isOnline(context)) {
            dateTime.text = getString(R.string.date_time, DateTime.getDateTime())
        } else {
            dateTime.text = date
        }
        lLayout.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        swipeRefresh.setOnRefreshListener {
            if (InternetConnection.isOnline(requireContext())) {
                adapter.clearCurrency()
                GlobalScope.launch { getCurrency() }
            } else {
                swipeRefresh.isRefreshing = false
                Toast.makeText(requireContext(), R.string.no_internet, Toast.LENGTH_LONG).show()
            }
        }
    }
}
