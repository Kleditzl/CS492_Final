package com.example.group_13_final.data

import com.squareup.moshi.Json
import java.io.Serializable



data class CurrentUser(
    @Json(name = "display_name") val name: String,
    val images: List<CurrentUserPicture>,
    val followers: CurrentUserFollowers,
    @Json(name = "id") val id: String,
    @Json(name = "uri") val userURI: String
):Serializable

data class CurrentUserPicture(
    @Json(name = "url") val url: String,
    @Json(name = "height") val height: Int?,
    @Json(name = "width") val width: Int?,
):Serializable

data class CurrentUserFollowers(
    //@Json(name = "href") val href: String?,
    @Json(name = "total") val total: Int
):Serializable
