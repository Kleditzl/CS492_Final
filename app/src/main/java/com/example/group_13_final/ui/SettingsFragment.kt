package com.example.group_13_final.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.example.group_13_final.R

class SettingsFragment: PreferenceFragmentCompat(){
    private val music_key = arrayOf("C","C#", "D","D#", "E", "F","F#","G","G#","A", "A#","B")
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).title = "Settings"
    }

}