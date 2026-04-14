package com.example.codingcliniclogicchallenge

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _array = MutableStateFlow<List<Int>>(emptyList())
    val array = _array.asStateFlow()


    fun update(number: Int) {
        _array.value += number
    }
}