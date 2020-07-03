package com.example.criptomoedas.ui.bcash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BcashViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Bcash Fragment"
    }
    val text: LiveData<String> = _text
}