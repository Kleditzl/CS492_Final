package com.example.group_13_final.api

import com.example.group_13_final.data.CurrentPlaylists
import com.squareup.moshi.Moshi
import com.squareup.moshi.Moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface PlaylistService {
    @GET("playlists")
    suspend fun loadPlaylists(
        @Header("Authorization") token : String
    ) : CurrentPlaylists

    companion object{
        private const val BASE_URL = "https://api.spotify.com/v1/me/"
        fun create() : PlaylistService {
            val moshi = Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(PlaylistService::class.java)
        }
    }
}