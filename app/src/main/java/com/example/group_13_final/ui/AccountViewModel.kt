package com.example.group_13_final.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.group_13_final.api.SpotifyAccountService
import com.example.group_13_final.data.CurrentUser
import com.example.group_13_final.data.CurrentUserRepository
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private val account = CurrentUserRepository(SpotifyAccountService.create())

    private val _account = MutableLiveData<CurrentUser?>(null)
    val acnt: LiveData<CurrentUser?> = _account
    /*
  * These fields hold an error object to be used to display an error message in the UI if
  * there was an error making an API call to OpenWeather.
  */
    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?> = _error

    /*
     * These fields hold a boolean value indicating whether an API call is currently loading.
     */
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun loadAccountData(token:String) {
        viewModelScope.launch {
            _loading.value = true
            val result = account.loadUserAccount(token)
            Log.d("loadAccountData", result.toString())
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _account.value = result.getOrNull()
        }
    }
}