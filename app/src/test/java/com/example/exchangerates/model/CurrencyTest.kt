package com.example.exchangerates.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CurrencyTest {

    private val currency = Currency("dollar", "2.666", "2.228", "2.244")

    init {
        currency.id = 7
    }

    @Test
    fun getId() {
        val id = currency.id
        assertEquals(currency.id, id)
    }

    @Test
    fun setId() {
        val id = 7
        currency.id = id
        assertEquals(id, currency.id)
    }

    @Test
    fun getName() {
        val name = currency.name
        assertEquals(currency.name, name)
    }

    @Test
    fun setName() {
        val name = "euro"
        currency.name = name
        assertEquals(name, currency.name)
    }

    @Test
    fun getBuying() {
        val buying = currency.buying
        assertEquals(currency.buying, buying)
    }

    @Test
    fun setBuying() {
        val buying = "2.555"
        currency.buying = buying
        assertEquals(buying, currency.buying)
    }

    @Test
    fun getSale() {
        val sale = currency.sale
        assertEquals(currency.sale, sale)
    }

    @Test
    fun setSale() {
        val sale = "2.444"
        currency.sale = sale
        assertEquals(sale, currency.sale)
    }

    @Test
    fun getNBank() {
        val nBank = currency.nBank
        assertEquals(currency.nBank, nBank)
    }

    @Test
    fun setNBank() {
        val nBank = "2.333"
        currency.nBank = nBank
        assertEquals(nBank, currency.nBank)
    }
}