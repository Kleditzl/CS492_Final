package com.example.group_13_final.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.bumptech.glide.Glide
import com.example.group_13_final.R
import com.example.group_13_final.data.Playlist
import com.example.group_13_final.data.Token
import com.spotify.android.appremote.api.SpotifyAppRemote

class AccountDetailsFragment: Fragment(R.layout.account_details) {
    private val playlistAdapter = PlayListAdapter(::playlistItemClick)
    //private val userAdapter = AccountAdapter()
    private var spotifyAppRemote: SpotifyAppRemote? = null
    private val accountViewModel: AccountViewModel by viewModels()
    private lateinit var requestQueue: RequestQueue
    private lateinit var playlistRV: RecyclerView
    private val main_vm: MainViewModel by activityViewModels()
    private val playlist_vm: UserPlaylistViewModel by viewModels()
    private lateinit var mAccessToken: Token
    private val tags = "AccountDetails Fragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).title = "Account"
        playlistRV = view.findViewById(R.id.playlist_results)
        playlistRV.layoutManager = LinearLayoutManager(requireContext())
        playlistRV.setHasFixedSize(true)
        playlistRV.adapter = playlistAdapter

        val uplaylist = view.findViewById<RecyclerView>(R.id.playlist_results)
        uplaylist.layoutManager = LinearLayoutManager(requireContext())

        main_vm.token.observe(viewLifecycleOwner){
            if(it != null) {
                mAccessToken = it
                Log.d(tags, "it ${mAccessToken.accessToken}")
                accountViewModel.loadAccountData("Bearer ${mAccessToken.accessToken}")
                playlist_vm.loadPlaylistSearchResults("Bearer ${mAccessToken.accessToken}")
                //accountSearch()
                //playlist()
                playlistRV.scrollToPosition(0)

            }

        }
        main_vm.spotifyAppRemote.observe(viewLifecycleOwner){
            if(it != null){
                spotifyAppRemote = it
            }
        }
        accountViewModel.acnt.observe(viewLifecycleOwner){
            if(it != null){
                val image = view.findViewById(R.id.imageView) as ImageView
                val url = it.images.get(0).url
                Glide.with(this).load(url).into(image)
                val nameTV : TextView = view.findViewById(R.id.account_name) as TextView
                nameTV.text = it.name
                val idTV : TextView = view.findViewById(R.id.id) as TextView
                idTV.text = "id: " + it.id
                val uriTV : TextView = view.findViewById(R.id.userURL) as TextView
                uriTV.text = "User URI: " + it.userURI
                val followersTV : TextView = view?.findViewById(R.id.followers) as TextView
                followersTV.text = "Followers: " + it.followers.total.toString()
            }
        }
        playlist_vm.searchResults.observe(viewLifecycleOwner){
            if(it != null){
                playlistAdapter.updatePlaylist(it)
            }

        }


    }

    private fun playlistItemClick(item: Playlist){
        val intent: Intent = Uri.parse(item.uri).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(intent)
    }

    override fun onResume() {
        Log.d(tag, "onResume()")
        super.onResume()

    }

    override fun onPause() {
        Log.d(tag, "onPause()")
        super.onPause()
    }
}