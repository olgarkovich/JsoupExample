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
import com.example.exchangerates.adapter.BankAdapter
import com.example.exchangerates.api.ApiResponse
import com.example.exchangerates.model.Bank
import com.example.exchangerates.repository.BankRepository
import com.example.exchangerates.repository.CurrencyRepository
import com.example.exchangerates.tools.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BankFragment : Fragment() {

    private lateinit var list: RecyclerView
    private lateinit var adapter: BankAdapter
    private lateinit var bankList: ArrayList<Bank>
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var repository: BankRepository
    private lateinit var progressBar: ProgressBar
    private lateinit var lLayout: LinearLayout
    private lateinit var dateTime: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bank, container, false)
        swipeRefresh = view.findViewById(R.id.bankRefresh)
        progressBar = view.findViewById(R.id.progressBarBank)
        progressBar.visibility = View.VISIBLE


        lLayout = view.findViewById(R.id.linearLayoutBank)
        dateTime = view.findViewById(R.id.dateTimeBank)
        list = view.findViewById(R.id.bankList)

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
        repository = BankRepository(requireContext(), GlobalScope)

        bankList = ArrayList()
        adapter = BankAdapter(requireContext(), bankList)
        list.adapter = adapter

        GlobalScope.launch { getBank() }
    }

    private fun getBank() {
        repository.loadAll(bankList)
        val date = DateTimeStorage.getDateTime(
            requireContext(), BANK_DATE_TIME, DATE_TIME_VALUE)
        requireActivity().runOnUiThread { onLoadChange(date) }
    }

    private fun onLoadChange(date: String?) {
        if (InternetConnection.isOnline(context)) {
            dateTime.text = getString(R.string.date_time, DateTime.getDateTime())
        } else {
            dateTime.text = date
        }
        lLayout.visibility = View.VISIBLE
        swipeRefresh.isRefreshing = false
        progressBar.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        swipeRefresh.setOnRefreshListener {
            if (InternetConnection.isOnline(requireContext())) {
                adapter.clearBank()
                GlobalScope.launch { getBank() }
            } else {
                swipeRefresh.isRefreshing = false
                Toast.makeText(requireContext(), R.string.no_internet, Toast.LENGTH_LONG).show()
            }
        }
    }
}
