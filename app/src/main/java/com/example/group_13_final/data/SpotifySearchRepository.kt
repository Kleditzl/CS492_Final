package com.example.group_13_final.data

class SpotifySearchRepository(
    private val dao: SpotifyDao
) {
    suspend fun insertBookmarkedRepo(artist: currentArtistDetail) = dao.insert(artist)
    suspend fun removeBookmarkedRepo(artist: currentArtistDetail) = dao.delete(artist)
    //fun getAllBookmarkedRepos() = dao.getAllSearches()
    //fun getBookmarkedRepoByName(name: String) = dao.getRepoByName(name)
}