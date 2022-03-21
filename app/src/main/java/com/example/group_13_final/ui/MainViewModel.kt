package com.example.group_13_final.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.group_13_final.data.Token
import com.spotify.android.appremote.api.SpotifyAppRemote

class MainViewModel : ViewModel() {
    val token = MutableLiveData<Token?>(null)
    val spotifyAppRemote = MutableLiveData<SpotifyAppRemote?>(null)
    fun addToken(token_input: Token ){
        token.value = token_input

    }

    fun addRemote(newspotifyAppRemote: SpotifyAppRemote){
        spotifyAppRemote.value = newspotifyAppRemote
    }


}