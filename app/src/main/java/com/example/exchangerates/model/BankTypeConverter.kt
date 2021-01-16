package com.example.exchangerates.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BankTypeConverter {
    @TypeConverter
    fun fromStringToList(data: String?): ArrayList<CurrencyPair> {
        val list = object : TypeToken<ArrayList<CurrencyPair>>() {}.type

        return Gson().fromJson(data, list)
    }

    @TypeConverter
    fun fromListToString(list: ArrayList<CurrencyPair>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}