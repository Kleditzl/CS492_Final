package com.example.group_13_final.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.group_13_final.R
import com.example.group_13_final.data.Playlist
import com.example.group_13_final.data.currentAlbumsList
import com.example.group_13_final.data.currentTrack
import com.squareup.picasso.Picasso

class SearchAdapter (private val onClick: (currentAlbumsList) -> Unit):
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

    var track = listOf<currentAlbumsList>()


    fun updateRepoList(newtracks: currentTrack?) {

        if (newtracks != null) {
            track = newtracks.tracks.items!!
        }
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int{
        return track.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_list_item, parent, false)
        return ViewHolder(itemView, onClick)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(track[position])
    }

    class ViewHolder(itemView: View, val onClick: (currentAlbumsList) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val artistNameTV: TextView = itemView.findViewById(R.id.artist_name)
        private val trackNameTV: TextView = itemView.findViewById(R.id.track_name)
        private var currentTrack: currentAlbumsList? = null

        init{
            itemView.setOnClickListener{
                currentTrack?.let(onClick)
            }
        }
        fun bind(TrackDetail: currentAlbumsList) {
            currentTrack = TrackDetail
            trackNameTV.text = TrackDetail.track_name
            artistNameTV.text = TrackDetail.artists[0].name

        }
    }
}