package com.example.group_13_final.data

import com.squareup.moshi.Json
import java.io.Serializable

data class CurrentPlaylists(
    val items: List<Playlist>
)

data class Playlist(
    @Json(name = "name") val name: String,
    val images: List<PlaylistPicture>,
    val tracks: Tracks,
    @Json(name = "uri") val uri: String

): Serializable
data class Tracks(
    @Json(name = "total") val total: Int,
): Serializable

data class PlaylistPicture(
    @Json(name = "url") val url: String,
    @Json(name = "height") val height: Int?,
    @Json(name = "width") val width: Int?,
): Serializable