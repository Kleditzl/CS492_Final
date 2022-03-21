package com.example.group_13_final.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.group_13_final.R
import com.example.group_13_final.data.RecList
import com.example.group_13_final.data.Recs

class RecAdapter(private val onClick: (Recs) -> Unit):
    RecyclerView.Adapter<RecAdapter.ViewHolder>() {

    private var recList = listOf<Recs>()

    fun updateRecList(newRec: RecList?){
        Log.d("recUpdate", "we here")
        recList = newRec!!.tracks ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = recList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("RecAdapter", "Rec adapter onCreate is called")
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rec_item, parent, false)
        return ViewHolder(itemView, onClick)
    }

    class ViewHolder(itemView: View, val onClick: (Recs) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.rec_name)
        private val a_nameTV: TextView = itemView.findViewById(R.id.artist_name)


        private var currentRec: Recs? = null

        init{
            itemView.setOnClickListener {
                currentRec?.let(onClick)
            }
        }


        fun bind(recList: Recs){
            currentRec = recList
            nameTV.text = recList.t_name
            a_nameTV.text = recList.artists[0].a_name
        }


    }

}