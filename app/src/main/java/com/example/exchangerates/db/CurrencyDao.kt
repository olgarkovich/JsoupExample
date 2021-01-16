package com.example.exchangerates.db

import androidx.room.*
import com.example.exchangerates.model.Currency

@Dao
interface CurrencyDao {

    @Insert
    fun insert(currency: Currency)

    @Insert
    fun insertAllCurrency(currencyList: List<Currency>)

    @Update
    fun updateAllCurrency(currencyList: List<Currency>)

    @Query("DELETE FROM currencyTable")
    fun deleteAllCurrency()

    @Query("SELECT * FROM currencyTable WHERE sale!='' ORDER BY name DESC")
    fun loadAllCurrency(): List<Currency>

    @Query("SELECT * FROM currencyTable WHERE sale='' LIMIT 1")
    fun loadDateTime(): Currency

}