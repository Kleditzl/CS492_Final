package com.example.group_13_final.api


import com.example.group_13_final.data.CurrentUser
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface SpotifyAccountService {
    @GET("me")
    suspend fun searchMe(@Header("Authorization") token : String) : CurrentUser

    companion object {
        private const val BASE_URL = "https://api.spotify.com/v1/"
        fun create() :SpotifyAccountService {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(SpotifyAccountService::class.java)
        }
    }
}