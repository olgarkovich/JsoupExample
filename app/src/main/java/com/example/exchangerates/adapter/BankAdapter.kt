package com.example.exchangerates.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.R
import com.example.exchangerates.model.Bank

class BankAdapter (context: Context, private val list: ArrayList<Bank>
) : RecyclerView.Adapter<BankAdapter.ViewHolder> () {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.list_item_bank, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    override fun getItemCount(): Int = list.size

    private fun getItem(position: Int) : Bank = list[position]

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.bankName)
        private val currencies: ArrayList<TextView> = arrayListOf(
            itemView.findViewById(R.id.bankBuyingDollar),  itemView.findViewById(R.id.bankSaleDollar),
            itemView.findViewById(R.id.bankBuyingEuro), itemView.findViewById(R.id.bankSaleEuro),
            itemView.findViewById(R.id.bankBuyingRuble), itemView.findViewById(R.id.bankSaleRuble))
//        private val buyingDollar: TextView = itemView.findViewById(R.id.bankBuyingDollar)
//        private val sellDollar: TextView = itemView.findViewById(R.id.bankSaleDollar)
//        private val buyingEuro: TextView = itemView.findViewById(R.id.bankBuyingEuro)
//        private val sellEuro: TextView = itemView.findViewById(R.id.bankSaleEuro)
//        private val buyingRuble: TextView = itemView.findViewById(R.id.bankBuyingRuble)
//        private val sellRuble: TextView = itemView.findViewById(R.id.bankSaleRuble)

        fun bind(bank: Bank) {
            name.text = bank.name
            for (i in 0 until bank.currencies.size - 1) { //without value USD -> EURO
                currencies[2 * i].text = bank.currencies[i].buying
                currencies[2 * i + 1].text = bank.currencies[i].sale
            }
        }
    }
}
