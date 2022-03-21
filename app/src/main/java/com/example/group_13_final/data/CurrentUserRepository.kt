package com.example.group_13_final.data

import com.example.group_13_final.api.SpotifyAccountService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrentUserRepository(private val service: SpotifyAccountService,
                            private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private var user: CurrentUser? = null
    private var t: String? = null
    suspend fun loadUserAccount(token: String) : Result<CurrentUser> {

        return if (t == token && user != null) {
            Result.success(user!!)
        } else {
            t = token
            withContext(ioDispatcher) {
                try {
                    val account = service.searchMe(token)
                    user = account
                    Result.success(account)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        }
    }
}