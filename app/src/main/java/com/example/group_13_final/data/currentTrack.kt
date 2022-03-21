package com.example.group_13_final.data

import com.squareup.moshi.Json
import java.io.Serializable

data class currentTrack(
    @Json(name = "tracks") val tracks: currentTracksList,
    //@Json(name = "artists") val artists: currentArtistsList,
    //@Json(name = "album") val album: currentAlbumsList
) :Serializable
