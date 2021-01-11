package com.example.exchangerates.ui.bank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BankViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bank Fragment"
    }
    val text: LiveData<String> = _text
}