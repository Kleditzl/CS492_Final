package com.example.group_13_final.data

import android.util.Log
import com.example.group_13_final.api.SearchService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception



class SearchTrackRepository(private val service: SearchService,
                            private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    private var query: String? = null
    private var cachedSearch: currentTrack? = null


    suspend fun loadSearchResults(
        q: String?,
        token: String?
    ) : Result<currentTrack> {
        return if (query == q && cachedSearch != null) {
            Result.success(cachedSearch!!)
        } else {
            withContext(ioDispatcher) {
                query = q
                withContext(ioDispatcher) {
                    try {
                        val track = "track"
                        val market = "US"
                        Log.d("SearchTrackRepository", "${q}, ${token}")
                        val results =
                            service.loadcurrentTracksList("Bearer " + token!!, q!!, track, market)
                        cachedSearch = results
                        Log.d("SearchTrackRepository", results.toString())
                        Result.success(results)
                    } catch (e: Exception) {
                        Log.d("SearchTrackRepository", e.toString())
                        Result.failure(e)
                    }

                }

            }
        }
    }
}