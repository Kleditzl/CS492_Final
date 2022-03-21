package com.example.group_13_final.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.group_13_final.R


//const val CLIENT_ID = "580f64364ffe48bb8ef9441a3f72a618"
//private const val REDIRECT_URI = "http://com.yourdomain.yourapp/callback"
//private val mSpotifyAppRemote: SpotifyAppRemote? = null

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        layout.close()
        setContentView(R.layout.activity_settings)
        setContentView(R.layout.activity_main)

    }
}