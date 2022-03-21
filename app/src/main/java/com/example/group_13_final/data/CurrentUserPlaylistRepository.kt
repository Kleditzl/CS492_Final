package com.example.group_13_final.data

import com.example.group_13_final.api.PlaylistService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CurrentUserPlaylistRepository (private val service: PlaylistService, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    private var plist : CurrentPlaylists? = null
    private var t: String? = null

    suspend fun loadUserPlaylist(token: String) : Result<CurrentPlaylists> {

        return if(t == token && plist != null) {
            Result.success(plist!!)
        }else {
            t = token
            withContext(ioDispatcher) {
                try {
                    val userplaylist = service.loadPlaylists(token)
                    plist = userplaylist
                    Result.success(userplaylist)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        }
    }
}