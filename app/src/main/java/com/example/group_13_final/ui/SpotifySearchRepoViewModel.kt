package com.example.group_13_final.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.group_13_final.data.AppDatabase
import com.example.group_13_final.data.SpotifySearchRepository
import com.example.group_13_final.data.currentArtistDetail
import kotlinx.coroutines.launch

class SpotifySearchRepoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SpotifySearchRepository(
        AppDatabase.getInstance(application).spotifyDao()
    )

    //val bookmarkedRepos = repository.getAllBookmarkedRepos().asLiveData()

    fun addSearchRepo(artist: currentArtistDetail) {
        viewModelScope.launch {
            repository.insertBookmarkedRepo(artist)
        }
    }

    fun removeSearchRepo(artist: currentArtistDetail) {
        viewModelScope.launch {
            repository.removeBookmarkedRepo(artist)
        }
    }
    /*
    fun getBookmarkedRepoByName(name: String) =
        repository.getBookmarkedRepoByName(name).asLiveData()
     */
}