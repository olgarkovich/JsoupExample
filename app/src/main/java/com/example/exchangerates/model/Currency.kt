package com.example.exchangerates.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "currencyTable")
data class Currency(
    var name: String,
    var buying: String,
    var sale: String,
    var nBank: String
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}
