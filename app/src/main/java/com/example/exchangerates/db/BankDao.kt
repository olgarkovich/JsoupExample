package com.example.exchangerates.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.exchangerates.model.Bank

@Dao
interface BankDao {

    @Insert
    fun insert(bank: Bank)

    @Insert
    fun insertAllBank(bankList: List<Bank>)

    @Update
    fun updateAllBank(bankList: List<Bank>)

    @Query("DELETE FROM bankTable")
    fun deleteAllBank()

    @Query("SELECT * FROM bankTable ")
    fun loadAllBank(): List<Bank>

}
