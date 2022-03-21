package com.example.group_13_final.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.group_13_final.R
import com.example.group_13_final.data.CurrentUser

class AccountAdapter ():
    RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    lateinit var currentUser: CurrentUser
    fun updateUser(newUser: CurrentUser?){
        currentUser = newUser!!
        notifyDataSetChanged()
    }

    override fun getItemCount() = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_details, parent, false)
        return AccountAdapter.ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: AccountAdapter.ViewHolder, position: Int) {
        holder.bind(currentUser)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageTv: ImageView = itemView.findViewById(R.id.imageView)
        //private val url
        val ctx = itemView.context
        private val name: TextView = itemView.findViewById(R.id.account_name)
        private val followers: TextView = itemView.findViewById(R.id.followers)
        private val url: TextView = itemView.findViewById(R.id.userURL)
        private val identification: TextView = itemView.findViewById(R.id.id)
        private var currentUser: CurrentUser? = null

        fun bind(user: CurrentUser){
            currentUser = user
            Glide.with(ctx).load(user.images.get(0).url).into(imageTv)
            name.text = user.name
            identification.text = user.id
            followers.text = "Followers: "+ user.followers.total.toString()
        }
    }
}