package com.example.group_13_final.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.group_13_final.api.RecService
import com.example.group_13_final.data.RecList
import com.example.group_13_final.data.RecRepository
import kotlinx.coroutines.launch

class RecViewModel : ViewModel() {
    private val rec = RecRepository(RecService.create())

    private val _rec = MutableLiveData<RecList?>(null)
    val recResults: LiveData<RecList?> = _rec

    private val _error = MutableLiveData<Throwable>(null)
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun loadRecResults(token: String, genre: String, energy: Int, dance: Int, temp: Int){
        viewModelScope.launch {
            _loading.value = true
            val result = rec.loadReclist(token, genre, energy, dance,temp)
            Log.d("recResults", result.toString())
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _rec.value = result.getOrNull()
        }
    }
}