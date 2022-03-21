package com.example.group_13_final.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.group_13_final.api.PlaylistService
import com.example.group_13_final.data.CurrentPlaylists
import com.example.group_13_final.data.CurrentUserPlaylistRepository
import com.example.group_13_final.data.CurrentUserRepository
import kotlinx.coroutines.launch

class UserPlaylistViewModel : ViewModel() {
    private val repository = CurrentUserPlaylistRepository(PlaylistService.create())
    private val _searchresults = MutableLiveData<CurrentPlaylists?>(null)
    val searchResults : LiveData<CurrentPlaylists?> = _searchresults
    private val _error = MutableLiveData<Throwable>(null)
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun loadPlaylistSearchResults(token:String){
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadUserPlaylist(token)
            Log.d("SearchPlaylistViewModel", "Result = ${result}")
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _searchresults.value = result.getOrNull()
        }
    }

}