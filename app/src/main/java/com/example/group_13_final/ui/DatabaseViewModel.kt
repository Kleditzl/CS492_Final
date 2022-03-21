package com.example.group_13_final.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.group_13_final.data.AppDatabase
import com.example.group_13_final.data.SpotifySearchRepository

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {
    private val searches = SpotifySearchRepository(AppDatabase.getInstance(application).spotifyDao())
    //val savedSearches = searches.getAllBookmarkedRepos().asLiveData()
}