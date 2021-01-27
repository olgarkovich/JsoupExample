package com.example.exchangerates.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class CurrencyPairTest {

    private val pair = CurrencyPair("1.11", "2.22")

    @Test
    fun getBuying() {
        val buying = pair.buying
        assertEquals(buying, pair.buying)
    }

    @Test
    fun getSale() {
        val sale = pair.sale
        assertEquals(sale, pair.sale)
    }
}