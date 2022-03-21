package com.example.group_13_final.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import java.io.Serializable


data class currentTracksList(
    @Json(name = "href")
    val href: String?,
    @Json(name = "items") var items: List<currentAlbumsList>?
    //@Json(name = "album") val album: currentAlbumsList
) : Serializable


data class currentArtistsList(
    @Json(name = "href") val href: String?
) : Serializable


data class currentAlbumsList(
    @Json(name = "album_type") val album_type: String?,
    @Json(name = "name") val track_name: String?,
    @Json(name = "uri") val uri: String?,
    @Json(name = "artists")
    val artists: List<currentArtistDetail>,
    ) : Serializable


@Entity
data class currentArtistDetail(
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    @PrimaryKey
    @NonNull
    val name: String
) : Serializable


class CurrentTrackListJsonAdapter {
    @FromJson
    fun currentTrackListFromJson(list : currentTracksList) = currentTracksList(
        href = list.href,
        items = list.items,
    )
}