package com.example.exchangerates.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class BankTest {

    private val bank = Bank("belarusbank", arrayListOf(
        CurrencyPair("2.12", "2.13"),
        CurrencyPair("3.12", "3.14"),
        CurrencyPair("3.77", "3.84")
    ))

    init {
        bank.id = 7
    }

    @Test
    fun getId() {
        val id = bank.id
        assertEquals(id, bank.id)
    }

    @Test
    fun setId() {
        val id = 3
        bank.id = id
        assertEquals(bank.id, id)
    }

    @Test
    fun getName() {
        val name = bank.name
        assertEquals(name, bank.name)
    }

    @Test
    fun getCurrencies() {
        val list = bank.currencies
        assertEquals(list, bank.currencies)
    }
}