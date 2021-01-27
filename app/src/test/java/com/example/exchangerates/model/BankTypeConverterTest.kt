package com.example.exchangerates.model

import com.google.gson.Gson
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BankTypeConverterTest {

    private val list = arrayListOf(
        CurrencyPair("2.12", "2.13"),
        CurrencyPair("3.12", "3.14"),
        CurrencyPair("3.77", "3.84")
    )

//    @Test
//    fun fromStringToList(data: String?) {
//        val list = object : TypeToken<ArrayList<CurrencyPair>>() {}.type
//        val a = Gson().fromJson(data, list)
//    }

    @Test
    fun fromListToString() {
        val gson = Gson()
        assertEquals(gson.toJson(list), gson.toJson(list))
    }
}