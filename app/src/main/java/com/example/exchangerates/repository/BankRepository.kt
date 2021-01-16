package com.example.exchangerates.repository

import android.content.Context
import com.example.exchangerates.api.ApiResponse
import com.example.exchangerates.db.AppDatabase
import com.example.exchangerates.db.BankDao
import com.example.exchangerates.model.Bank
import com.example.exchangerates.tools.*
import kotlinx.coroutines.CoroutineScope

class BankRepository(private val context: Context, scope: CoroutineScope) {

    private val database: AppDatabase = AppDatabase.getDatabase(context, scope)
    private val bankDao: BankDao = database.bankDao()

    fun loadAll(bankList: ArrayList<Bank>) {
        if (InternetConnection.isOnline(context)) {
            ApiResponse.getBank(bankList)
            storeData(bankList)
        } else {
            bankList.clear()
            bankList.addAll(bankDao.loadAllBank())
        }
    }

    private fun insertAll(bankList: List<Bank>) {
        bankDao.insertAllBank(bankList)
    }

    private fun deleteAll() {
        bankDao.deleteAllBank()
    }

    private fun storeData(bankList: ArrayList<Bank>) {
        deleteAll()
        insertAll(bankList)
        DateTimeStorage.setDateTime(context, BANK_DATE_TIME, DATE_TIME_VALUE)
    }
}
