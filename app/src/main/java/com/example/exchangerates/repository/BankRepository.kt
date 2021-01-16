package com.example.exchangerates.repository

import android.content.Context
import com.example.exchangerates.api.ApiResponse
import com.example.exchangerates.db.AppDatabase
import com.example.exchangerates.db.BankDao
import com.example.exchangerates.db.CurrencyDao
import com.example.exchangerates.model.Bank
import com.example.exchangerates.model.Currency
import com.example.exchangerates.tools.DateTime
import com.example.exchangerates.tools.InternetConnection
import kotlinx.coroutines.CoroutineScope

class BankRepository(private val context: Context, scope: CoroutineScope) {

    private val database: AppDatabase = AppDatabase.getDatabase(context, scope)
    private val bankDao: BankDao = database.bankDao()

    fun loadAll(bankList: ArrayList<Bank>) {
        if (InternetConnection.isOnline(context)) {
            ApiResponse.getBank(bankList)
            deleteAll()
            insertAll(bankList)
        } else {
            bankList.clear()
            bankList.addAll(bankDao.loadAllBank())
        }
    }

//    private fun insertDateTime() {
//        currencyDao.insert(Currency(DateTime.getDateTime(), "", "", ""))
//    }

//    fun loadDateTime(): String {
//        return currencyDao.loadDateTime().name
//    }

    private fun insertAll(bankList: List<Bank>) {
        bankDao.insertAllBank(bankList)
    }

    private fun deleteAll() {
        bankDao.deleteAllBank()
    }
}
