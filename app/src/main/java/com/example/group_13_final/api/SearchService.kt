package com.example.group_13_final.api

import android.util.Log
import com.example.group_13_final.data.CurrentTrackListJsonAdapter
import com.example.group_13_final.data.currentTrack
import com.example.group_13_final.data.currentTracksList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchService {
    @GET("search")
    suspend fun loadcurrentTracksList(
        /*@Header("Accept") accept:String,
        @Header("Content-Type") content : String,*/
        @Header("Authorization") token : String,
        @Query("q") query: String,
        @Query("type") track: String,
        @Query("market") market: String
    ) : currentTrack

    companion object {
        private const val BASE_URL = "https://api.spotify.com/v1/"
        fun create() :SearchService {
            val moshi = Moshi.Builder()
                .add(CurrentTrackListJsonAdapter())
                .addLast(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(SearchService::class.java)
        }
    }
}