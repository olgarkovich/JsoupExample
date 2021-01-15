package com.example.exchangerates.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.R
import com.example.exchangerates.model.Currency

class CurrencyAdapter (context: Context, private val list: ArrayList<Currency>
) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder> () {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.list_item_currency, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    override fun getItemCount(): Int = list.size

    private fun getItem(position: Int) : Currency = list[position]

    fun addCurrency(currencyList: ArrayList<Currency>) {
        list += currencyList
        notifyDataSetChanged()
    }

    fun clearCurrency() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.currencyName)
        private val buying: TextView = itemView.findViewById(R.id.currencyBuying)
        private val sale: TextView = itemView.findViewById(R.id.currencySale)
        private val nBank: TextView = itemView.findViewById(R.id.currencyNBank)

        fun bind(currency: Currency) {
            name.text = currency.name
            buying.text = currency.buying
            sale.text = currency.sale
            nBank.text = currency.nBank
        }
    }
}
