package com.example.group_13_final.data


import android.util.Log
import com.example.group_13_final.api.RecService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class RecRepository (private val service: RecService, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    private var rec : RecList? = null
    private var t : String? = null
    private var genre: String? = null
    private var energy: Int? = null
    private var dance: Int? = null
    private var temp: Int? = null
    suspend fun loadReclist(token: String, sent_genre: String, sent_energy: Int, sent_dance: Int, sent_temp: Int) : Result <RecList>{
        return if(sent_dance == dance && sent_energy == energy && sent_temp == temp && genre == sent_genre && t == token && rec != null){
            Result.success(rec!!)
        }else{
            t = token
            energy = sent_energy
            dance = sent_dance
            temp = sent_temp
            withContext(ioDispatcher){
                try{
                    val result = service.loadRecItems("Bearer " + token, 20,  "US", sent_genre, "${sent_dance}","${sent_energy}","${sent_temp}" )
                    Log.d("recRepo", result.toString())
                    genre = sent_genre
                    rec = result
                    Result.success(result)
                } catch (e:Exception){
                    Result.failure(e)
                }
            }
        }
    }
}