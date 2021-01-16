package com.example.exchangerates.repository

import android.content.Context
import com.example.exchangerates.api.ApiResponse
import com.example.exchangerates.db.AppDatabase
import com.example.exchangerates.db.CurrencyDao
import com.example.exchangerates.model.Currency
import com.example.exchangerates.tools.*
import kotlinx.coroutines.CoroutineScope

class CurrencyRepository(private val context: Context, scope: CoroutineScope) {

    private val database: AppDatabase = AppDatabase.getDatabase(context, scope)
    private val currencyDao: CurrencyDao = database.currencyDao()

    fun loadAll(currencyList: ArrayList<Currency>) {
        if (InternetConnection.isOnline(context)) {
            ApiResponse.getCurrency(currencyList)
            storeData(currencyList)
        } else {
            currencyList.clear()
            currencyList.addAll(currencyDao.loadAllCurrency())
        }
    }

//    private fun insertDateTime() {
//        currencyDao.insert(Currency(DateTime.getDateTime(), "", "", ""))
//    }
//
//    fun loadDateTime(): String {
//        return currencyDao.loadDateTime().name
//    }

    private fun insertAll(currencyList: List<Currency>) {
        currencyDao.insertAllCurrency(currencyList)
    }

    private fun deleteAll() {
        currencyDao.deleteAllCurrency()
    }

    private fun storeData(currencyList: ArrayList<Currency>) {
        deleteAll()
        insertAll(currencyList)
        DateTimeStorage.setDateTime(context, CURRENCY_DATE_TIME, DATE_TIME_VALUE)
    }
}
