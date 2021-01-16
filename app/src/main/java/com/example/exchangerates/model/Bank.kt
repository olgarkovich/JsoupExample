package com.example.exchangerates.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "bankTable")
@TypeConverters(BankTypeConverter::class)
data class Bank(
    val name: String,
    val currencies: ArrayList<CurrencyPair>
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}

