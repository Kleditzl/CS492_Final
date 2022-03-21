package com.example.group_13_final.data

import com.squareup.moshi.Json
import java.io.Serializable

data class RecList(val tracks: List<Recs>)

data class Recs(
    @Json(name = "name")val t_name: String,
    val artists: List<RecArt>,
    val uri: String
): Serializable

data class RecArt(
    @Json(name = "name")  val a_name: String,
    val uri : String
):Serializable
