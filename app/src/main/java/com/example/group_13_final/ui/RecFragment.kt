package com.example.group_13_final.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.group_13_final.R
import com.example.group_13_final.data.Recs
import com.example.group_13_final.data.Token
import com.spotify.android.appremote.api.SpotifyAppRemote

class RecFragment : Fragment(R.layout.rec_page) {

    private val recAdapter = RecAdapter(::recItemClick)
    private lateinit var recRV: RecyclerView
    private lateinit var mAccessToken: Token
    private var spotifyAppRemote: SpotifyAppRemote? = null
    private val main_vm: MainViewModel by activityViewModels()
    private val rec_vm: RecViewModel by viewModels()
    private var genre: String? = null
    private var maxEnergy: Int? = null
    private var maxDance: Int? = null
    private var maxTempo: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("recFragment", "RecFragment is called")
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).title = "Recommendations"
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        recRV = view.findViewById(R.id.rec_results)
        recRV.layoutManager = LinearLayoutManager(requireContext())
        recRV.setHasFixedSize(true)
        recRV.adapter = recAdapter
        main_vm.token.observe(viewLifecycleOwner){
            if(it != null) {
                Log.d("recFragment","getting token")
                mAccessToken = it
                genre = sharedPrefs.getString(getString(R.string.pref_genre_key),
                    "country,classical,hip-hip,pop,indie")
                maxEnergy = sharedPrefs.getInt(getString(R.string.pref_max_energy_key), 100)
                maxDance = sharedPrefs.getInt(getString(R.string.pref_dance_key), 100)
                maxTempo = sharedPrefs.getInt(getString(R.string.pref_max_tempo_key), 100)

                rec_vm.loadRecResults(mAccessToken.accessToken, genre!!, maxEnergy!!, maxDance!!, maxTempo!!)
            }

        }
        main_vm.spotifyAppRemote.observe(viewLifecycleOwner){
            if(it != null){
                spotifyAppRemote = it
            }
        }

        rec_vm.recResults.observe(viewLifecycleOwner){
            if(it != null){
                recAdapter.updateRecList(it)
            }
        }

    }

    private fun recItemClick(item: Recs){
        val intent: Intent = Uri.parse(item.uri!!).let{ webpage ->
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