package com.example.group_13_final.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SpotifyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artist: currentArtistDetail)

    @Delete
    suspend fun delete(artist: currentArtistDetail)

    //@Query("SELECT * FROM currentArtistDetail")
    //fun getAllSearches(): Flow<List<SearchedTracks>>


}