package com.example.group_13_final

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.group_13_final.data.*
import com.example.group_13_final.ui.*
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.security.MessageDigest
import java.security.SecureRandom
import kotlin.collections.HashMap


class SpotifyMainFragment : Fragment(R.layout.spotify_main) {
    private val TAG = "MainActivityFragment"
    private var spotifyAppRemote: SpotifyAppRemote? = null
    private var baseurl = "https://api.spotify.com/v1/"
    private lateinit var searchBoxET: EditText
    private lateinit var searchButton : Button
    private val redirectUri = "http://localhost/"
    private val clientId = "580f64364ffe48bb8ef9441a3f72a618"
    lateinit var mAccessToken : String
    private lateinit var playListAdapter: PlayListAdapter
    private lateinit var authorizationCode :String
    var CODE_VERIFIER = getCodeVerifier()
    private val viewModel: MainViewModel by activityViewModels()
    private val searchVM: SearchViewModel by viewModels()
    private val searchRepoVM: SpotifySearchRepoViewModel by viewModels()
    private val searchAdapter = SearchAdapter(::trackItemClick)
    private lateinit var searchResultsListRV: RecyclerView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBoxET = view.findViewById(R.id.et_search_box)
        searchButton = view.findViewById(R.id.btn_search)
        searchErrorTV = view.findViewById(R.id.tv_loading_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        searchResultsListRV = view.findViewById(R.id.rv_search)
        searchResultsListRV.layoutManager = LinearLayoutManager(requireContext())
        searchResultsListRV.setHasFixedSize(true)
        searchResultsListRV.adapter = searchAdapter

        (activity as AppCompatActivity).title = "Main page"
        if (!this::mAccessToken.isInitialized) {
            showLoginActivityCode.launch(getLoginActivityCodeIntent())
        }
        searchVM.searchResults.observe(viewLifecycleOwner){ searchResult ->
            searchAdapter.updateRepoList(searchResult)
        }

        searchVM.loadingStatus.observe(viewLifecycleOwner){ uiState ->
            when(uiState){
                LoadingStatus.LOADING -> {
                    loadingIndicator.visibility = View.VISIBLE
                    searchResultsListRV.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                }
                LoadingStatus.ERROR -> {
                    loadingIndicator.visibility = View.INVISIBLE
                    searchResultsListRV.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.VISIBLE
                }
                else -> {
                    loadingIndicator.visibility = View.INVISIBLE
                    searchResultsListRV.visibility = View.VISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                }
            }
        }

        Log.d(TAG, "before show LoginActivityToken")
        searchButton.setOnClickListener{
            val query = searchBoxET.text.toString()
            //searchVM.loadSearchResults(query, mAccessToken)
            val result = searchVM.loadSearchResults(query, mAccessToken)
            Log.d("query results: ", result.toString())
            val currentTrackObj = currentArtistDetail("1", query.toString())
            searchRepoVM.addSearchRepo(currentTrackObj)
        }


    }
    private fun trackItemClick(item: currentAlbumsList){
//        Log.d("play: ", item.uri!!)
//        spotifyAppRemote?.let{
//            val playlistURI = item.uri
//            it.playerApi.play(playlistURI)
//
//        }
        val intent: Intent = Uri.parse(item.uri).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.activity_main, menu)
    }



    companion object{ //Everything below here was found here in order to access token webapi https://stackoverflow.com/questions/68750229/how-to-impement-spotifys-authorization-code-with-pkce-in-kotlin

        private fun getCodeVerifier(): String {
            val secureRandom = SecureRandom()
            val code = ByteArray(64)
            secureRandom.nextBytes(code)
            return Base64.encodeToString(
                code,
                Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
            )
        }
        fun getCodeChallenge(verifier: String): String {
            val bytes = verifier.toByteArray()
            val messageDigest = MessageDigest.getInstance("SHA-256")
            messageDigest.update(bytes, 0, bytes.size)
            val digest = messageDigest.digest()
            return Base64.encodeToString(
                digest,
                Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
            )
        }
    }

    fun getLoginActivityCodeIntent(): Intent =
        AuthorizationClient.createLoginActivityIntent(
            activity,
            AuthorizationRequest.Builder(clientId, AuthorizationResponse.Type.CODE, redirectUri)
                .setScopes(
                    arrayOf(
                       "user-library-read", "user-library-modify",
                        "app-remote-control", "user-read-currently-playing"
                    )
                )
                .setCustomParam("code_challenge_method", "S256")
                .setCustomParam("code_challenge", getCodeChallenge(CODE_VERIFIER))
                .build()
        )
    private val showLoginActivityCode = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        Log.d(TAG, "getting the code, is it working?")
        val authorizationResponse = AuthorizationClient.getResponse(result.resultCode, result.data)
        when (authorizationResponse.type) {
            AuthorizationResponse.Type.CODE ->{
                authorizationCode = authorizationResponse.code
                Log.d(TAG, "got the authorization code! ${authorizationCode}")
            }
            // Here You will get the authorization code which you
            // can get with authorizationResponse.code
            AuthorizationResponse.Type.ERROR -> error("oops")
            // Handle the Error
            // Probably interruption
        }
        Log.d(TAG, "got the authorization code! ${authorizationCode}")
        showLoginActivityToken.launch(getLoginActivityTokenIntent(authorizationCode))
    }
    fun getLoginActivityTokenIntent(code: String): Intent =
        AuthorizationClient.createLoginActivityIntent(
            activity,
            AuthorizationRequest.Builder(clientId, AuthorizationResponse.Type.TOKEN, redirectUri)
                .setCustomParam("grant_type", "authorization_code")
                .setCustomParam("code", code)
                .setCustomParam("code_verifier", CODE_VERIFIER)
                .build()
        )
    private val showLoginActivityToken = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        val authorizationResponse = AuthorizationClient.getResponse(result.resultCode, result.data)

        when (authorizationResponse.type) {
            AuthorizationResponse.Type.TOKEN -> {
                // Here You can get access to the authorization token
                // with authorizationResponse.token
                mAccessToken = authorizationResponse.accessToken
                viewModel.addToken(Token(mAccessToken))
                Log.d(TAG, "got the authorization code! ${mAccessToken}")
            }
            AuthorizationResponse.Type.ERROR -> error("accessToken was not good!?")//Probably interruption
        }
    }

}