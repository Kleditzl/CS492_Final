package com.example.group_13_final.api

import com.example.group_13_final.data.RecList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecService {
    @GET("recommendations")
    suspend fun loadRecItems(
        @Header("Authorization") token : String,
        @Query("limit") limit: Int,
        @Query("market") market: String,
        @Query("seed_genres") genre : String,
        @Query("max_danceability") dance: String,
        @Query("max_energy") energy: String,
        @Query("max_tempo") temp: String
    ) : RecList


    companion object{
        private const val BASE_URL = "https://api.spotify.com/v1/"
        fun create(): RecService{
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(RecService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(RecService::class.java)
        }
    }
}