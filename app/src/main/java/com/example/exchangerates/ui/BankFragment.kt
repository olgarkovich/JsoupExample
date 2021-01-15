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
import com.example.exchangerates.tools.DateTime
import com.example.exchangerates.tools.InternetConnection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BankFragment : Fragment() {

    private lateinit var list: RecyclerView
    private lateinit var adapter: BankAdapter
    private lateinit var bankList: ArrayList<Bank>
    private lateinit var swipeRefresh: SwipeRefreshLayout
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

        if (InternetConnection.isOnline(requireContext())) {
            lLayout = view.findViewById(R.id.linearLayoutBank)
            dateTime = view.findViewById(R.id.dateTimeBank)
            list = view.findViewById(R.id.bankList)

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
        bankList = ArrayList()
        adapter = BankAdapter(requireContext(), bankList)
        list.adapter = adapter

        GlobalScope.launch { getBank() }
    }

    private fun getBank() {
        ApiResponse.getBank(bankList)
        requireActivity().runOnUiThread { onLoadChange() }
    }

    private fun onLoadChange() {
        lLayout.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        dateTime.text = getString(R.string.date_time, DateTime.getDateTime())
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        swipeRefresh.setOnRefreshListener {
            adapter.clearBank()
            GlobalScope.launch { getBank() }
        }
    }
}
