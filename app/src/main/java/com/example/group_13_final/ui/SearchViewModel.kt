package com.example.group_13_final.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.group_13_final.data.SearchTrackRepository
import com.example.group_13_final.api.SearchService
import com.example.group_13_final.data.LoadingStatus
import com.example.group_13_final.data.currentTrack
import com.example.group_13_final.data.currentTracksList
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = SearchTrackRepository(SearchService.create())
    private val _searchResults = MutableLiveData<currentTrack?>(null)
    val searchResults: LiveData<currentTrack?> = _searchResults

    private val _error = MutableLiveData<Throwable>(null)
    val error: LiveData<Throwable> = _error

    private val _loadingStatus = MutableLiveData(LoadingStatus.SUCCESS)
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    fun loadSearchResults(q: String, token: String){
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.loadSearchResults(q, token)
            Log.d("SearchViewModel", "Result = ${result}")
            _searchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true ->  LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }

        }
    }
}