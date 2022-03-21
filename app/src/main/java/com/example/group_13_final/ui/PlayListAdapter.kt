package com.example.group_13_final.ui


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.group_13_final.R
import com.example.group_13_final.data.*
import com.squareup.picasso.Picasso

class PlayListAdapter (private val onClick: (Playlist) -> Unit):
    RecyclerView.Adapter<PlayListAdapter.ViewHolder>(){

    lateinit var token: Token
    var playList = listOf<Playlist>()
    var currentUser = listOf<CurrentUser>()

    fun updatePlaylist(newPlayList: CurrentPlaylists?) {
        playList = newPlayList!!.items ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = playList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.playlist, parent, false)
        return ViewHolder(itemView, onClick)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playList[position])
    }

    class ViewHolder(itemView: View, val onClick: (Playlist) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val imageTV: ImageView = itemView.findViewById(R.id.playlist_image)
        private val nameTV: TextView = itemView.findViewById(R.id.playlist_name)
        //private val uriTV: TextView = itemView.findViewById(R.id.playlist_uri)
        private val tracksTV: TextView = itemView.findViewById(R.id.playlist_tracks_num)

        private var currentPlaylist: Playlist? = null

        init{
            itemView.setOnClickListener{
                currentPlaylist?.let(onClick)
            }
        }


        fun bind(playlistDetail: Playlist) {
            Log.d("message", playlistDetail.name)
            currentPlaylist = playlistDetail
            val url = playlistDetail.images[0].url
            Picasso.get().load(url).into(imageTV);
            nameTV.text = playlistDetail.name
            tracksTV.text = "Tracks: " + playlistDetail.tracks.total.toString()
        }
    }
}